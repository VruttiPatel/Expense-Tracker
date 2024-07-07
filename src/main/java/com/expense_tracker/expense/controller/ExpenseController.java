package com.expense_tracker.expense.controller;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.expense.dto.Expense;
import com.expense_tracker.expense.service.ExpenseService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Aspect
@RequestMapping("/expense")
public class ExpenseController {

	public final ExpenseService expenseService;

	@PostMapping("/addExpense")
	public Expense addExpense(@Valid @RequestBody Expense expense, HttpServletRequest request,
			@RequestParam long cashbookId) throws Exception {
		return expenseService.addExpense(expense, request, cashbookId);
	}

	@DeleteMapping("/deleteExpense")
	public String deleteExpense(@RequestParam long expenseId, HttpServletRequest request) {
		return expenseService.deleteExpense(expenseId, request);
	}

	@GetMapping("/getAllExpenses")
	public List<Expense> getAllExpenses(@RequestParam long cashbookId) {
		return expenseService.getAllExpenses(cashbookId);
	}

	@PutMapping("/updateExpense")
	public Expense updateExpense(@Valid @RequestBody Expense expense, HttpServletRequest request,
			@RequestParam long expenseId) throws Exception {
		return expenseService.updateExpense(expense, request, expenseId);
	}
}
