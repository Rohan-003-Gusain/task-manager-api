package com.taskmanager.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.taskmanager.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private long tokenValidity;

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
    
    // ========== GENERATE JWT TOKEN ==========
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(getSigningKey()) 
                .compact();
    }

    // ========== EXTRACT USERNAME FROM TOKEN ==========
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ========== EXTRACT CLAIM FROM TOKEN ==========
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    // ========== PARSE ALL CLAIM ==========
    private Claims extractAllClaims(String token) {
        try {
        	return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
		} catch (Exception e) {
			throw new RuntimeException("Invalid JWT token");
		}
    }

    // ========== VALIDATE JWT TOKEN ==========
    public boolean validateToken(String token, UserDetails userDetails) {
    	
    		final String username = extractUsername(token);
    		
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    
    // ========== CHECK TOKEN EXPIRATION ==========
    private boolean isTokenExpired(String token) {
    	
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
