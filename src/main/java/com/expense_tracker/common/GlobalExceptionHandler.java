package com.expense_tracker.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		return new ResponseEntity<>(errorResponse, errorResponse.errorCode);
	}

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errors = new HashMap<>();

		e.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String errorMsg = error.getDefaultMessage();
			errors.put(field, errorMsg);
		});

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, errors);
		return new ResponseEntity<>(errorResponse, errorResponse.errorCode);
	}

}
