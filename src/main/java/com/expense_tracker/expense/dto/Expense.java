package com.expense_tracker.expense.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

	@NotNull(message = "Please fill out the amount field")
	private Double amount;

	private long cashbookId;

	@NotBlank(message = "Entry type is required")
	private String entryType;

	@NotNull(message = "Entry date time is required")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date entryDateTime;

	private String remarks;

	@NotBlank(message = "Category required")
	private String category;

	@NotBlank(message = "Payment mode is required")
	private String paymentMode;
	
}
