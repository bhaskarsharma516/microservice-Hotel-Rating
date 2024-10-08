package com.pro.gateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.pro.gateway.config.UserPrinciple;
import com.pro.gateway.repo.UserRepo;

import reactor.core.publisher.Mono;

@Service
public class MyUserDetailsService implements ReactiveUserDetailsService{

	@Autowired
	private UserRepo userRepo;
	

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userRepo.findByUsername(username).map(UserPrinciple::new);

				
	}

}
