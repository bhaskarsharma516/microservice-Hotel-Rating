package com.pro.gateway.services;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtHelper {

	// requirement :
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	private static final KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);
	private static final PrivateKey privateKey = keyPair.getPrivate();


	// generate token for user
	public String generateToken(Authentication authentication) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, authentication);
	}

	private String doGenerateToken(Map<String, Object> claims, Authentication authentication) {

		return Jwts.builder().setSubject(authentication.getName()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(privateKey, SignatureAlgorithm.RS256).compact();
	}
		
	  public  static String getPublicKey() {
	        var publicKey= keyPair.getPublic();
	        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	    }

}