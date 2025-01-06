package com.project2.sec.Service;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtService {

	public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
	
	public String extractUsername(String token) {
		return null;
	}
	
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractA
//	}	
//	private Claims extractAllClaims(String token) {
//		return Jwts
//				.builder()
//				.signWith(getSignKey())
//				.build()
//				.par
//	}
}
