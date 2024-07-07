package com.expense_tracker.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {

	private String token;

    private long expiresIn;
    
    private long userId;

}
