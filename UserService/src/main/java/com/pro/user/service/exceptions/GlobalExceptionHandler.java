package com.pro.user.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pro.user.service.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
		String message =ex.getMessage();
		var response=ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UsernameAlreadyExistException.class)
	public ResponseEntity<ApiResponse> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex){
		String message =ex.getMessage();
		var response=ApiResponse.builder().message(message).success(true).status(HttpStatus.FOUND).build();
		return new ResponseEntity<>(response,HttpStatus.FOUND);
	}
}
