package com.expense_tracker.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPassword {
	
	@NotBlank(message = "Please fill out the current password field")
	public String currentPassword;
	
	@NotBlank(message = "Please fill out the new password field")
	@Size(min = 8, message = "Minimum 8 character password is required")
	@Size(max = 20, message = "Maximum 20 characters only allowed")
	public String newPassword;

}
