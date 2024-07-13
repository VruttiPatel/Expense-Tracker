package com.expense_tracker.auth.service;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.expense_tracker.auth.dto.RegisterUser;
import com.expense_tracker.auth.dto.ResetPassword;
import com.expense_tracker.auth.dto.UserLogin;
import com.expense_tracker.auth.entity.UserMst;
import com.expense_tracker.auth.repo.UserRepo;
import com.expense_tracker.constant.ExpenseTrackerConstants;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

	private final UserRepo userRepo;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	public AuthenticationService(UserRepo userRepo, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	public void signup(RegisterUser input, HttpServletRequest request) throws Exception {

		if (userRepo.findByEmail(input.getEmail()).isPresent()) {
			throw new Exception("This email is already registered!");
		}
		UserMst user = new UserMst();
		user.setUserName(input.getUserName());
		user.setEmail(input.getEmail());
		user.setPassword(passwordEncoder.encode(input.getPassword()));
		user.setDob(input.getDob());
		user.setStatus(1);
		user.setCreatedByIp(request.getRemoteAddr());
		user.setCreatedDate(new Date());

		user = userRepo.save(user);

		long userId = user.getUserId();

		MultipartFile file = input.getFile();

		String filePath = ExpenseTrackerConstants.ATTACHMENT_PATH;
		String fileName = UUID.randomUUID().toString();
		FileOutputStream fout = new FileOutputStream(filePath + fileName);
		fout.write(file.getBytes());
		fout.close();

		user.setProfilePicId(fileName);
		user.setCreatedBy(userId);

		userRepo.save(user);

	}

	public UserMst authenticate(UserLogin input) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));

		return userRepo.findByEmail(input.getEmail()).orElseThrow();
	}

	public String resetPassword(ResetPassword resetPassword, HttpServletRequest request) throws Exception {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (!userRepo.findByEmail(email).isPresent()) {
			throw new Exception("User not valid");
		}

		UserMst user = userRepo.findByEmail(email).get();

		// if(!user.getPassword().equals(passwordEncoder.encode(resetPassword.getCurrentPassword())))
		if (!passwordEncoder.matches(resetPassword.getCurrentPassword(), user.getPassword())) {
			throw new Exception("Your current password is not matching");
		} else if (resetPassword.getNewPassword().equals(resetPassword.getCurrentPassword())) {
			throw new Exception("New password is same as the current password");
		}

		user.setPassword(passwordEncoder.encode(resetPassword.getNewPassword()));
		user.setUpdatedBy(user.getUserId());
		user.setUpdatedDate(new Date());
		user.setUpdatedByIp(request.getLocalAddr());
		userRepo.save(user);

		return "Password changed successfully";
	}

}
