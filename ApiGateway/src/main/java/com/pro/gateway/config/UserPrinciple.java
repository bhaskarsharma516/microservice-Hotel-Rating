package com.pro.gateway.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pro.gateway.models.User;

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
//		return user.map(obj->{
//			return obj.getPassword();
//	}).toString();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
//	 return user.map(obj->{
//			return obj.getUsername();
//		}).toString();
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
