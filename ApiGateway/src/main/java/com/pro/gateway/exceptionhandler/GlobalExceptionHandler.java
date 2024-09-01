package com.pro.gateway.exceptionhandler;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pro.gateway.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponse> AuthenticationExceptionHandler(AuthenticationException ex){
		return new ResponseEntity<ApiResponse>(ApiResponse.builder()
				.message(ex.getMessage())
				.success(true)
				.status(HttpStatus.NOT_FOUND)
				.build(),HttpStatus.NOT_FOUND);
	}
}
