package com.pro.gateway.config;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.pro.gateway.services.JwtHelper;
import com.pro.gateway.services.MyUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class JwtAuthenticationFilter implements WebFilter  {

  
    @Autowired
    private JwtHelper jwtHelper;
    

    @Autowired
    private MyUserDetailsService userDetailsService;

   
    private static final List<String> PUBLIC_PATHS = List.of("/login");


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		 String requestPath = exchange.getRequest().getURI().getPath();
		
		if (PUBLIC_PATHS.stream().anyMatch(requestPath::startsWith)) {
            return chain.filter(exchange); // Continue without authentication for public paths
        }
		 String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		 String username = null,authToken;
		  if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            // Extract token from header (assuming "Bearer <token>")
	             authToken = authHeader.substring(7);
	           
	            
	            try {

	                username = this.jwtHelper.getUsernameFromToken(authToken);

	            } catch (IllegalArgumentException e) {
	                
	                e.printStackTrace();
	            } catch (ExpiredJwtException e) {
	               
	                e.printStackTrace();
	            } catch (MalformedJwtException e) {
	                
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();

	            }


	        } else {
	        	return Mono.error(new RuntimeException("Invalid Error!"));
	        }
		  UserDetails userDetails = null;
		  if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			 
			try {
				userDetails = this.userDetailsService.findByUsername(username).toFuture().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	            Boolean validateToken = this.jwtHelper.validateToken(authToken, userDetails);
	            if (validateToken) {
	            	System.out.println("validated");

	            	SecurityContext context = new SecurityContextImpl(new UsernamePasswordAuthenticationToken(userDetails, authToken, userDetails.getAuthorities()));
	            	return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
	            	

	            } else {
	            	return Mono.error(new RuntimeException("Validation Failed"));
	            }


	        }

		  return chain.filter(exchange);

		  }

	}

    

    
