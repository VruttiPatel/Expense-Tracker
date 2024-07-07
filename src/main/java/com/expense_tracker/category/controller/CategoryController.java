package com.expense_tracker.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.category.dto.Category;
import com.expense_tracker.category.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

	private final CategoryService categoryService;

	@PostMapping("/createCategory")
	public Category createCategory(@Valid @RequestBody Category category, HttpServletRequest request,
			@RequestParam long cashbookId) throws Exception {
		return categoryService.createCategory(category, request, cashbookId);
	}

	@GetMapping("/getCategories")
	public List<Category> getAllActiveCategories(@RequestParam long cashbookId) {
		return categoryService.getAllActiveCategories(cashbookId);
	}
}
