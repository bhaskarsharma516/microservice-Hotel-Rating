package com.pro.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	   @Autowired
	   private JwtAuthenticationFilter filter;
	   
	   @Autowired
	   private ReactiveUserDetailsService userDetailsService;
	    
	
		@Bean
		 SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
			http.csrf(csrf->csrf.disable());
			http.cors(cors->cors.disable());
			http.addFilterBefore(filter, SecurityWebFiltersOrder.AUTHENTICATION);
		   http
				.authorizeExchange(exchanges -> exchanges
		                .pathMatchers("/login").permitAll()
		                .anyExchange().authenticated());
		    
		   return http.build();

	   }

	  

	    @Bean
	    ReactiveAuthenticationManager authenticationManager() throws Exception {
	        return new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
	    }
	    

	   
	  }
