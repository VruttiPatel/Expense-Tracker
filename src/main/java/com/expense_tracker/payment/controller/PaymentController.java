package com.expense_tracker.payment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.payment.dto.Payment;
import com.expense_tracker.payment.service.PaymentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;

	@PostMapping("/addPaymentMode")
	public Payment addPaymentMode(@Valid @RequestBody Payment payment, HttpServletRequest request,
			@RequestParam long cashbookId) throws Exception {

		return paymentService.addPaymentMode(payment, request, cashbookId);
	}

	@GetMapping("/getAllActivePaymentModes")
	public List<Payment> getAllActivePaymentModes(@RequestParam long cashbookId) {
		return paymentService.getAllActivePaymentModes(cashbookId);
	}

}
