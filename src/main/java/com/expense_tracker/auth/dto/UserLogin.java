package com.expense_tracker.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserLogin {
	
	@NotBlank(message="Please fill out Email field")
	private String email;
	
	@NotBlank(message="Please fill out Password field")
	private String password;
}
