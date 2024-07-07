package com.expense_tracker.category.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.expense_tracker.cashbook.entity.CashbookMst;
import com.expense_tracker.category.entity.CategoryMst;

public interface CategoryRepo extends CrudRepository<CategoryMst, Long> {

	Optional<CategoryMst> findByCategoryName(String name);

	boolean existsByCategoryNameAndCashbookMst(String categoryName, CashbookMst cashbookMst);

	List<CategoryMst> findByCashbookMstCashbookIdAndStatus(long cashbookId, long status);

	Optional<CategoryMst> findByCategoryNameAndStatus(String categoryName,long status);

	CategoryMst findByCategoryId(long categoryId);
}
