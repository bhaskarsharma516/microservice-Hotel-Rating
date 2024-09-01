package com.pro.gateway.config;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pro.gateway.models.User;

import reactor.core.publisher.Mono;

public class UserPrinciple implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private  Mono<User> user;
	
	public UserPrinciple(Mono<User> user2) {
		this.user=user2;	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority authority=new SimpleGrantedAuthority("ROLE_USER");
		return List.of(authority);
		
	}

	@Override
	public String getPassword() {
		
		return "{bcrypt}"+user.map(obj->{
			return obj.getPassword();
	}).toString();
	}

	@Override
	public String getUsername() {
		
	 return user.map(obj->{
			return obj.getUsername();
		}).toString();
	}

}
