package com.expense_tracker.cashbook.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.auth.repo.UserRepo;
import com.expense_tracker.cashbook.dto.Cashbook;
import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.cashbook.repo.CashbookRepo;
import com.expense_tracker.constant.ExpenseTrackerConstants;
import com.expense_tracker.expense.repo.ExpenseRepo;
import com.expense_tracker.expense.service.ExpenseService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CashbookService {

	private final CashbookRepo cashbookRepo;

	private final UserRepo userRepo;

	private final ExpenseService expenseService;

	public Cashbook createCashbook(Cashbook cashbook, String emailId, HttpServletRequest request) throws Exception {

		String cashbookName = cashbook.getCashbookName();

		UserMst user = getUserByEmailId(emailId);

		if (cashbookRepo.findByCashbookNameAndUserMstAndStatus(cashbookName, user, ExpenseTrackerConstants.ACTIVE).isPresent()) {
			throw new Exception("Cashbook " + cashbookName + " already created");
		}

		CashbookMst cashbookMst = new CashbookMst();
		// BeanUtils.copyProperties(cashbook, cashbookMst);

		cashbookMst = cashbookMst.builder().cashbookName(cashbook.getCashbookName()).userMst(user)
				.status(ExpenseTrackerConstants.ACTIVE).createdBy(user.getUserId()).createdDate(new Date())
				.createdByIp(request.getLocalAddr()).build();

		cashbookRepo.save(cashbookMst);

		BeanUtils.copyProperties(cashbookMst, cashbook);
		cashbook.setUserId(user.getUserId());

		return cashbook;
	}

	private UserMst getUserByEmailId(String emailId) {
		return userRepo.findByEmail(emailId).get();
	}

	public List<CashbookMst> getAllCashbooks() {
		return (List<CashbookMst>) cashbookRepo.findAll();
	}

	public List<Cashbook> getAllCashbooksByUserId(long user) {
		UserMst userData = userRepo.findByUserId(user).get();

		List<Cashbook> allCashbooks = new ArrayList<Cashbook>();

		List<CashbookMst> cashbooks = cashbookRepo.findByUserMstAndStatus(userData, ExpenseTrackerConstants.ACTIVE);

		for (CashbookMst cashbook : cashbooks) {
			Cashbook cashbookDto = new Cashbook();
			BeanUtils.copyProperties(cashbook, cashbookDto);
			cashbookDto.setExpense(expenseService.getAllExpenses(cashbookDto.getCashbookId()));
			cashbookDto.setUserId(cashbook.getUserMst().getUserId());
			allCashbooks.add(cashbookDto);
		}

		return allCashbooks;
	}

	public Cashbook updateCashbook(long cashbookId, Cashbook cashbook, HttpServletRequest request, String emailId)
			throws Exception {

		UserMst user = getUserByEmailId(emailId);

		if (!cashbookRepo.findByCashbookIdAndUserMstAndStatus(cashbookId, user, ExpenseTrackerConstants.ACTIVE)
				.isPresent()) {
			throw new Exception("Cashbook " + cashbook.getCashbookName() + " not present");
		}
		if (cashbookRepo
				.findByCashbookNameAndUserMstAndStatus(cashbook.getCashbookName(), user, ExpenseTrackerConstants.ACTIVE)
				.isPresent()) {
			throw new Exception("Cashbook " + cashbook.getCashbookName() + " already present");
		}
		CashbookMst existingCashbook = cashbookRepo.findById(cashbookId).get();

		existingCashbook.setCashbookName(cashbook.getCashbookName());
		existingCashbook.setUpdatedBy(user.getUserId());
		existingCashbook.setUpdatedByIp(request.getLocalAddr());
		existingCashbook.setUpdatedDate(new Date());

		cashbookRepo.save(existingCashbook);
		BeanUtils.copyProperties(existingCashbook, cashbook);
		cashbook.setUserId(existingCashbook.getUserMst().getUserId());
		return cashbook;
	}

	public String deleteCashbook(long cashbookId, String emailId) throws Exception {

		UserMst user = getUserByEmailId(emailId);

		if (!cashbookRepo.findByCashbookIdAndUserMstAndStatus(cashbookId, user, ExpenseTrackerConstants.ACTIVE)
				.isPresent()) {
			throw new Exception("Requested cashbook not present");
		}
		CashbookMst cashbook = cashbookRepo.findById(cashbookId).get();
		String cashbookName = cashbook.getCashbookName();

		cashbook.setStatus(ExpenseTrackerConstants.INACTIVE);
		cashbookRepo.save(cashbook);

		return "Cashbook - " + cashbookName + " deleted successfully";
	}

}
