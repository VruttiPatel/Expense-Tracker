package com.expense_tracker.category.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {

	@NotBlank(message = "Category name is required")
	private String categoryName;

	private long cashbookId;

}
