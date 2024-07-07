package com.expense_tracker.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.expense_tracker.auth.service.AuthenticationService;
import com.expense_tracker.auth.service.JwtService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AuthViewController {
	
	private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    @RequestMapping("/auth/registration")
    public String register(){
    	return "RegisterUser";
    }

}
