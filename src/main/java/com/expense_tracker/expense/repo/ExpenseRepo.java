package com.expense_tracker.expense.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense_tracker.expense.entity.ExpenseMst;

@Repository
public interface ExpenseRepo extends CrudRepository<ExpenseMst, Long> {

	List<ExpenseMst> findByExpenseIdAndStatus(long expenseId, long active);

	List<ExpenseMst> findByCashbookMstCashbookIdAndStatus(long cashbookId, long active);

}
