package com.example.Testify.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTToken {

	private final Key SECRETKEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private static final long EXPIRATION_TIME = 60000;

	public String generateToken(String name) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

		return Jwts.builder().setSubject(name).setIssuedAt(now).setExpiration(expiryDate).signWith(SECRETKEY)
				.compact();
	}

	public String validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRETKEY).build().parseClaimsJws(token);
			return "valid";
		} catch (ExpiredJwtException ex) {
			return "expired token";
		} catch (JwtException | IllegalArgumentException e) {
			return "invalid token";
		}
	}

}
