package com.expense_tracker.payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.cashbook.repo.CashbookRepo;
import com.expense_tracker.common.ExpenseUtils;
import com.expense_tracker.constant.ExpenseTrackerConstants;
import com.expense_tracker.payment.dto.Payment;
import com.expense_tracker.payment.entity.PaymentMst;
import com.expense_tracker.payment.repo.PaymentRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepo paymentRepo;

	private final CashbookRepo cashbookRepo;

	private final ExpenseUtils utils;

	public Payment addPaymentMode(Payment payment, HttpServletRequest request, long cashbookId) throws Exception {

		CashbookMst cashbook = cashbookRepo.findById(cashbookId).get();

		if (paymentRepo.existsByPaymentModeAndStatusAndCashbookMstCashbookId(payment.getPaymentMode(),
				ExpenseTrackerConstants.ACTIVE, cashbookId)) {
			throw new Exception("Payment mode " + payment.getPaymentMode() + " already added");
		}

		PaymentMst paymentMst = new PaymentMst();

		long userId = utils.getUserId();

		paymentMst = PaymentMst.builder().cashbookMst(cashbook).paymentMode(payment.getPaymentMode()).createdBy(userId)
				.createdDate(new Date()).createdByIp(request.getLocalAddr()).status(ExpenseTrackerConstants.ACTIVE)
				.build();

		paymentRepo.save(paymentMst);

		payment.setCashbookId(cashbookId);

		return payment;

	}

	public List<Payment> getAllActivePaymentModes(long cashbookId) {
		List<PaymentMst> paymentModeList = paymentRepo.findByCashbookMstCashbookIdAndStatus(cashbookId,
				ExpenseTrackerConstants.ACTIVE);

		List<Payment> paymentModes = new ArrayList<Payment>();

		for (int i = 0; i < paymentModeList.size(); i++) {
			Payment payment = new Payment();

			BeanUtils.copyProperties(paymentModeList.get(i), payment);

			payment.setCashbookId(cashbookId);

			paymentModes.add(payment);
		}

		return paymentModes;
	}

}
