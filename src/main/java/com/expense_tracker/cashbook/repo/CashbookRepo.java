package com.expense_tracker.cashbook.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.cashbook.entity.CashbookMst;

public interface CashbookRepo extends CrudRepository<CashbookMst, Long> {

	CashbookMst findByCashbookId(long cashbookId);

	List<CashbookMst> findByUserMstAndStatus(UserMst userData, long active);

	Optional<CashbookMst> findByCashbookIdAndUserMstAndStatus(long cashbookId, UserMst user, long active);

	Optional<CashbookMst> findByCashbookNameAndUserMstAndStatus(String cashbookName, UserMst user, long active);
}
