package com.pro.gateway.exceptionhandler;

import java.util.concurrent.ExecutionException;

import javax.naming.AuthenticationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pro.gateway.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiResponse> AuthenticationExceptionHandler(AuthenticationException ex){
		return new ResponseEntity<ApiResponse>(ApiResponse.builder()
				.message(ex.getMessage())
				.success(false)
				.status(HttpStatus.NOT_FOUND)
				.build(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({BadCredentialsException.class,InterruptedException.class,ExecutionException.class})
	public ResponseEntity<ApiResponse> BadCredentialsExceptionHandler(Exception ex){
		if(ex instanceof BadCredentialsException) {
			return new ResponseEntity<ApiResponse>(ApiResponse.builder()
				.message(ex.getMessage())
				.success(false)
				.status(HttpStatus.UNAUTHORIZED)
				.build(),HttpStatus.UNAUTHORIZED);
		}
		
		if(ex instanceof InterruptedException || ex instanceof ExecutionException) {
			return new ResponseEntity<ApiResponse>(ApiResponse.builder()
					.message(ex.getMessage())
					.success(false)
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.build(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ApiResponse>(ApiResponse.builder()
				.message(ex.getMessage())
				.success(false)
				.status(HttpStatus.BAD_REQUEST)
				.build(),HttpStatus.BAD_REQUEST);
	}
}
