package com.expense_tracker.expense.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.cashbook.repo.CashbookRepo;
import com.expense_tracker.category.dto.Category;
import com.expense_tracker.category.entity.CategoryMst;
import com.expense_tracker.category.repo.CategoryRepo;
import com.expense_tracker.category.service.CategoryService;
import com.expense_tracker.common.ExpenseUtils;
import com.expense_tracker.constant.ExpenseTrackerConstants;
import com.expense_tracker.expense.dto.Expense;
import com.expense_tracker.expense.entity.ExpenseMst;
import com.expense_tracker.expense.repo.ExpenseRepo;
import com.expense_tracker.payment.dto.Payment;
import com.expense_tracker.payment.entity.PaymentMst;
import com.expense_tracker.payment.repo.PaymentRepo;
import com.expense_tracker.payment.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {

	private final ExpenseRepo expenseRepo;

	private final CashbookRepo cashbookRepo;

	private final PaymentRepo paymentRepo;

	private final CategoryRepo categoryRepo;

	private final CategoryService categoryService;

	private final PaymentService paymentService;

	private final ExpenseUtils utils;

	public Expense addExpense(Expense expense, HttpServletRequest request, long cashbookId) throws Exception {

		CashbookMst cashbook = cashbookRepo.findByCashbookId(cashbookId);

		CategoryMst categoryMst = new CategoryMst();
		PaymentMst paymentMst = new PaymentMst();

		if (categoryRepo.findByCategoryNameAndStatus(expense.getCategory().trim(), ExpenseTrackerConstants.ACTIVE)
				.isPresent()) {
			categoryMst = categoryRepo
					.findByCategoryNameAndStatus(expense.getCategory().trim(), ExpenseTrackerConstants.ACTIVE).get();
		} else {
			Category category = new Category();
			category.setCategoryName(expense.getCategory());
			categoryService.createCategory(category, request, cashbookId);

			categoryMst = categoryRepo.findByCategoryName(expense.getCategory().trim()).get();
		}

		if (paymentRepo.findByPaymentModeAndStatusAndCashbookMstCashbookId(expense.getPaymentMode().trim(),
				ExpenseTrackerConstants.ACTIVE, cashbookId).isPresent()) {
			paymentMst = paymentRepo.findByPaymentModeAndStatusAndCashbookMstCashbookId(expense.getPaymentMode().trim(),
					ExpenseTrackerConstants.ACTIVE, cashbookId).get();
		} else {
			Payment payment = new Payment();
			payment.setPaymentMode(expense.getPaymentMode());
			paymentService.addPaymentMode(payment, request, cashbookId);

			paymentMst = paymentRepo.findByPaymentMode(expense.getPaymentMode().trim()).get();
		}

		long userId = utils.getUserId();

		var expenseMst = ExpenseMst.builder().cashbookMst(cashbook).categoryMst(categoryMst)
				.entryType(expense.getEntryType().equals("Cash In") ? ExpenseTrackerConstants.CASH_IN
						: ExpenseTrackerConstants.CASH_OUT)
				.entryDateTime(expense.getEntryDateTime()).amount(expense.getAmount()).paymentMst(paymentMst)
				.status(ExpenseTrackerConstants.ACTIVE).createdBy(userId).createdDate(new Date())
				.createdByIp(request.getLocalAddr()).remarks(expense.getRemarks()).build();

		expenseRepo.save(expenseMst);

		expense.setCashbookId(cashbookId);

		return expense;
	}

	public String deleteExpense(long expenseId, HttpServletRequest request) {

		ExpenseMst expense = expenseRepo.findById(expenseId).get();

		long userId = utils.getUserId();

		expense.setStatus(ExpenseTrackerConstants.INACTIVE);
		expense.setUpdatedBy(userId);
		expense.setUpdatedByIp(request.getLocalAddr());
		expense.setUpdatedDate(new Date());

		expenseRepo.save(expense);

		List<ExpenseMst> expenseList = expenseRepo.findByExpenseIdAndStatus(expenseId, ExpenseTrackerConstants.ACTIVE);

		if (expenseList.isEmpty()) {
			return "Expense deleted successfully";
		} else {
			return "Sorry! It seems there is some issue while deleting the expense";
		}
	}

	public List<Expense> getAllExpenses(long cashbookId) {
		List<ExpenseMst> expenseMst = expenseRepo.findByCashbookMstCashbookIdAndStatus(cashbookId,
				ExpenseTrackerConstants.ACTIVE);

		List<Expense> expenseList = new ArrayList<Expense>();

		for (ExpenseMst expense : expenseMst) {
			Expense expenseDto = new Expense();
			BeanUtils.copyProperties(expense, expenseDto);
			// if(expense.getCategoryMst().getCategoryId())
			expenseDto.setCategory(
					categoryRepo.findByCategoryId(expense.getCategoryMst().getCategoryId()).getCategoryName());
			expenseDto.setPaymentMode(
					paymentRepo.findById(expense.getPaymentMst().getPaymentId()).get().getPaymentMode());
			expenseDto.setCashbookId(cashbookId);
			expenseDto.setEntryType(expense.getEntryType() == 1 ? "Cash In" : "Cash Out");
			expenseList.add(expenseDto);
		}

		return expenseList;
	}

	public Expense updateExpense(@Valid Expense expense, HttpServletRequest request, long expenseId) throws Exception {
		ExpenseMst expenseMst = expenseRepo.findById(expenseId).get();
		
		//long userId = utils.getUserId();

		expenseMst.setEntryType(expense.getEntryType().equals("Cash In") ? ExpenseTrackerConstants.CASH_IN
				: ExpenseTrackerConstants.CASH_OUT);
		expenseMst.setEntryDateTime(expense.getEntryDateTime());
		expenseMst.setAmount(expense.getAmount());

		CashbookMst cashbook = expenseMst.getCashbookMst();

		CategoryMst categoryMst = new CategoryMst();
		PaymentMst paymentMst = new PaymentMst();

		if (categoryRepo.findByCategoryNameAndStatus(expense.getCategory().trim(), ExpenseTrackerConstants.ACTIVE)
				.isPresent()) {
			categoryMst = categoryRepo
					.findByCategoryNameAndStatus(expense.getCategory().trim(), ExpenseTrackerConstants.ACTIVE).get();
		} else {
			Category category = new Category();
			category.setCategoryName(expense.getCategory());
			categoryService.createCategory(category, request, cashbook.getCashbookId());

			categoryMst = categoryRepo.findByCategoryName(expense.getCategory().trim()).get();
		}

		if (paymentRepo.findByPaymentModeAndStatusAndCashbookMstCashbookId(expense.getPaymentMode().trim(),
				ExpenseTrackerConstants.ACTIVE, cashbook.getCashbookId()).isPresent()) {
			paymentMst = paymentRepo.findByPaymentModeAndStatusAndCashbookMstCashbookId(expense.getPaymentMode().trim(),
					ExpenseTrackerConstants.ACTIVE, cashbook.getCashbookId()).get();
		} else {
			Payment payment = new Payment();
			payment.setPaymentMode(expense.getPaymentMode());
			paymentService.addPaymentMode(payment, request, cashbook.getCashbookId());

			paymentMst = paymentRepo.findByPaymentMode(expense.getPaymentMode().trim()).get();
		}

		expenseMst.setCategoryMst(categoryMst);
		expenseMst.setPaymentMst(paymentMst);
		//expenseMst.setUpdatedBy(userId);
		expenseMst.setUpdatedDate(new Date());
		expenseMst.setUpdatedByIp(request.getLocalAddr());
		expenseMst.setRemarks(expense.getRemarks());

		expenseRepo.save(expenseMst);

		Expense expenseDto = new Expense();

		BeanUtils.copyProperties(expenseMst, expenseDto);
		
		expenseDto.setCashbookId(cashbook.getCashbookId());

		return expenseDto;
	}

}
