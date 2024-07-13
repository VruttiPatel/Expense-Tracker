package com.expense_tracker.auth.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUser {

	@NotBlank(message = "Username bhul gaye aap!")
	private String userName;

	@NotBlank(message = "Aree Email ke bina kaise chalega!")
	@Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Ab acha sa Email enter kar de yaar!")
	private String email;

	@NotBlank(message = "Yaar password to set kara hi nai!")
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\\\S+$)$", message = "Password secure nai lag raha hai :(")
	@Size(min = 8, message = "Minimum 8 character password is required")
	@Size(max = 20, message = "Maximum 20 characters only allowed")
	private String password;

	private Date dob;
	
	private MultipartFile file;
}
