package com.pro.gateway.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.pro.gateway.models.User;

import lombok.ToString;

@Component
@ToString
public class UserPrinciple implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private User user;
	
	public UserPrinciple(User user2) {
		this.user=user2;	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	return	Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return "{bcrypt}"+user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}
	
	  @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

}
