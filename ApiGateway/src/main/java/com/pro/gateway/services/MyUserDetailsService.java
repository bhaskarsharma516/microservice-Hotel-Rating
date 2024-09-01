package com.pro.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;

import com.pro.gateway.config.UserPrinciple;
import com.pro.gateway.models.User;
import com.pro.gateway.repo.UserRepo;

import reactor.core.publisher.Mono;

public class MyUserDetailsService implements ReactiveUserDetailsService{

	@Autowired
	private UserRepo userRepo;


	@Override
	public Mono<UserDetails> findByUsername(String username) {
		Mono<User> user=userRepo.findByUsername(username);
		UserDetails prin=new UserPrinciple(user);
		return Mono.just(prin);
			
						
			
						
						
		  
	
				
	
				
	}

}
