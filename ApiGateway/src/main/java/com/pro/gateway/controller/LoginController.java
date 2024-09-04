package com.pro.gateway.controller;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pro.gateway.dto.JwtResponse;
import com.pro.gateway.models.User;
import com.pro.gateway.services.JwtHelper;

import reactor.core.publisher.Mono;

@RestController
public class LoginController {
	
	@Autowired
	private ReactiveAuthenticationManager manager;
	
	@Autowired
	private JwtHelper helper;


	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody User user) throws InterruptedException, ExecutionException{

		UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());		 
		 Mono<Authentication> valid=	manager.authenticate(auth).onErrorReturn(auth);
	
			    Authentication result = valid.toFuture().get(); // Blocks until the Mono completes , its an alternative of Block()
			    System.out.println("Result from Mono: " + result);

		 if(result.isAuthenticated()) {
			String token=this.helper.generateToken(result);
			var res=JwtResponse.builder().username(user.getUsername()).token(token).build();
			return new ResponseEntity<>(res,HttpStatus.ACCEPTED);

		 }
		  return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}
	
	

}
