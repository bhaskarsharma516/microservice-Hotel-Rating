package com.pro.gateway.services;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtHelper {

    //requirement :
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

   
    private static Key key;
    
    @PostConstruct
    public  void init(){
    	key=Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
        
     }
    
    public Claims extractUserRole(String token) {
        return getAllClaimsFromToken(token);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
    	return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    			
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
	public String generateToken(Authentication authentication ) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, authentication);
    }

    private String doGenerateToken(Map<String, Object> claims, Authentication authentication) {
    	String role=authentication.getAuthorities().stream()
    			.map(r->r.getAuthority()).collect(Collectors.toSet()).iterator().next();

        return Jwts.builder().claim("role",role).setSubject(authentication.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

        Claims claims = getAllClaimsFromToken(token);

       final Collection<? extends GrantedAuthority> authorities =
               Arrays.stream(claims.get("role").toString().split(","))
                       .map(SimpleGrantedAuthority::new)
                       .collect(Collectors.toList());

       return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
   }

}