package com.expense_tracker.cashbook.dto;

import java.util.List;

import com.expense_tracker.expense.dto.Expense;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cashbook {

	private long cashbookId;

	@NotBlank(message = "Cashbook name is required")
	private String cashbookName;

	private long userId;

	private List<Expense> expense;

}
