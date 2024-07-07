package com.expense_tracker.cashbook.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.cashbook.dto.Cashbook;
import com.expense_tracker.cashbook.service.CashbookService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cashbook")
@RequiredArgsConstructor
public class CashbookController {

	private final CashbookService cashbookService;

	@PostMapping("/createCashbook")
	public Cashbook createCashbook(@Valid @RequestBody Cashbook cashbook, HttpServletRequest request) throws Exception {
		String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return cashbookService.createCashbook(cashbook, emailId, request);
	}

	@GetMapping("/getAllCashbook")
	public List<Cashbook> getAllCashbooks(@RequestParam("userId") long userId) {
		return cashbookService.getAllCashbooksByUserId(userId);
	}

	@PutMapping("/updateCashbook")
	public Cashbook updateCashbook(@RequestParam("cashbookId") long cashbookId, @Valid @RequestBody Cashbook cashbook,
			HttpServletRequest request) throws Exception {
		String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return cashbookService.updateCashbook(cashbookId, cashbook, request, emailId);
	}

	@DeleteMapping("/deleteCashbook")
	public String deleteCashbook(@RequestParam("cashbookId") long cashbookId) throws Exception {
		String emailId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return cashbookService.deleteCashbook(cashbookId,emailId);
	}
}
