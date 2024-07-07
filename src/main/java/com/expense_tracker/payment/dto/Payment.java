package com.expense_tracker.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

	private long cashbookId;

	@NotBlank(message = "Payment Mode is required")
	private String paymentMode;

}
