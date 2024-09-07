package com.pro.gateway.services;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	// requirement :
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	private static final PrivateKey privateKey = keyPair.getPrivate();


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
    	return Jwts.parserBuilder().setSigningKey(keyPair.getPublic()).build().parseClaimsJws(token).getBody();
    			
    }
	// generate token for user
	public String generateToken(Authentication authentication) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, authentication);
	}
	  //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

	private String doGenerateToken(Map<String, Object> claims, Authentication authentication) {

		return Jwts.builder().setSubject(authentication.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(privateKey, SignatureAlgorithm.RS256).compact();
	}
	
	 public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth, final UserDetails userDetails) {

	        

	       return new UsernamePasswordAuthenticationToken(userDetails, "", Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	   }
		
	  public  static String getPublicKey() {
	        var publicKey= keyPair.getPublic();
	        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	    }

}