package com.expense_tracker.common;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {

	public HttpStatus errorCode;

	public Object errorMsg;

}
