package com.pro.user.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	  
	    @Autowired
	    private JwtAuthenticationFilter filter;
	    
	
	   @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 //auth.requestMatchers("/").authenticated()
		   http.csrf(csrf->csrf.disable());
		   http.cors(cors->cors.disable());
		   http.authorizeHttpRequests(auth->
				   auth
				   .requestMatchers("/users/add").permitAll()
				   .anyRequest().authenticated());
		   
		
					//creating session on every request
	           http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
	        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		   
		  return  http.build();

	   }
	   
	   
	  }
