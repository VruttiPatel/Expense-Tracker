package com.expense_tracker.category.service;

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
import com.expense_tracker.common.ExpenseUtils;
import com.expense_tracker.constant.ExpenseTrackerConstants;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryRepo categoryRepo;

	private final CashbookRepo cashbookRepo;

	private final ExpenseUtils utils;

	public Category createCategory(Category category, HttpServletRequest request, long cashbookId)
			throws Exception {

		CashbookMst cashbook = cashbookRepo.findByCashbookId(cashbookId);

		if (categoryRepo.existsByCategoryNameAndCashbookMst(category.getCategoryName(), cashbook)) {
			throw new Exception("Category " + category.getCategoryName() + " already present");
		}

		long userId = utils.getUserId();

		CategoryMst categoryMst = new CategoryMst();

		BeanUtils.copyProperties(category, categoryMst);
		categoryMst.setStatus(ExpenseTrackerConstants.ACTIVE);
		categoryMst.setCashbookMst(cashbook);
		categoryMst.setCreatedBy(userId);
		categoryMst.setCreatedByIp(request.getLocalAddr());
		categoryMst.setCreatedDate(new Date());

		categoryRepo.save(categoryMst);

		category.setCashbookId(cashbookId);

		return category;
	}

	public List<Category> getAllActiveCategories(long cashbookId) {
		List<CategoryMst> categoryList = categoryRepo.findByCashbookMstCashbookIdAndStatus(cashbookId,
				ExpenseTrackerConstants.ACTIVE);

		List<Category> categories = new ArrayList<Category>();

		for (int i = 0; i < categoryList.size(); i++) {
			Category category = new Category();

			BeanUtils.copyProperties(categoryList.get(i), category);

			category.setCashbookId(cashbookId);

			categories.add(category);
		}

		return categories;
	}

}
