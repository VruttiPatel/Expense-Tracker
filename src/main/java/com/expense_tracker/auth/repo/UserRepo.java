package com.expense_tracker.auth.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.cashbook.entity.CashbookMst;

public interface UserRepo extends CrudRepository<UserMst, Integer>{
	
	Optional<UserMst> findByEmail(String email);

	Optional<UserMst> findByUserId(long user);

}
