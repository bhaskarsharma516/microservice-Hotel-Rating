package com.pro.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.pro.gateway.services.MyUserDetailsService;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
		@Bean
		 SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
			http.csrf(csrf->csrf.disable());
			http.cors(cors->cors.disable());
		   http
				.authorizeExchange(exchanges -> exchanges
		                .pathMatchers("/login/**","/users/add").permitAll()
		                .anyExchange().authenticated());
		   
		   
		 return http.build();

	   }

	    @Bean
	     ReactiveUserDetailsService userDetailsService() {
	     
	    	return new MyUserDetailsService();
	    }



	    @Bean
	    ReactiveAuthenticationManager authenticationManager() throws Exception {
	        return new UserDetailsRepositoryReactiveAuthenticationManager(this.userDetailsService());
	    }
	    

	   
	  }
