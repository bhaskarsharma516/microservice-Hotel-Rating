package com.pro.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public Mono<Authentication> login(@RequestBody User user){
		UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
	return	manager.authenticate(auth);
		
		
		
	
	}

}
