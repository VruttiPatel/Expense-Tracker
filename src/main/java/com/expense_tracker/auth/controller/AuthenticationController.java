package com.expense_tracker.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense_tracker.auth.dto.LoginResponse;
import com.expense_tracker.auth.dto.RegisterUser;
import com.expense_tracker.auth.dto.ResetPassword;
import com.expense_tracker.auth.dto.UserLogin;
import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.auth.service.AuthenticationService;
import com.expense_tracker.auth.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	private final JwtService jwtService;

	private final AuthenticationService authenticationService;

	public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
		this.jwtService = jwtService;
		this.authenticationService = authenticationService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterUser registerUser, HttpServletRequest request)
			throws Exception {

		authenticationService.signup(registerUser, request);

		return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody UserLogin userLogin) {
		UserMst authenticatedUser = authenticationService.authenticate(userLogin);

		String jwtToken = jwtService.generateToken(authenticatedUser);

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setToken(jwtToken);
		loginResponse.setExpiresIn(jwtService.getExpirationTime());
		loginResponse.setUserId(authenticatedUser.getUserId());

		return ResponseEntity.ok(loginResponse);
	}

	@PutMapping("/resetPassword")
	public String resetPassword(@Valid @RequestBody ResetPassword resetPassword, HttpServletRequest request) throws Exception {
		return authenticationService.resetPassword(resetPassword, request);
	}

}
