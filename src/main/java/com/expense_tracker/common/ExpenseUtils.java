package com.expense_tracker.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.expense_tracker.auth.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExpenseUtils {
	
	public final UserRepo userRepo;
	
	public long getUserId()
	{
		return getUserIdFromEmail(getEmailIdFromJWT());
	}
	
	public String getEmailIdFromJWT()
	{
		return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public long getUserIdFromEmail(String emailId)
	{
		return userRepo.findByEmail(emailId).get().getUserId();
	}

}
