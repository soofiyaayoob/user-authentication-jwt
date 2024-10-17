package com.example.demo.ServiceAdmin;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Service
public class jwtServiceAdmin {
	


	   private static String heresecretkey = "";

	    public jwtServiceAdmin() throws NoSuchAlgorithmException {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
	        SecretKey sk = keyGenerator.generateKey();
	        heresecretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
	    }

	    public static String generateToken(String username) {
	        Map<String, Object> claims = new HashMap<>();
	        String token =Jwts.builder()
	                .setClaims(claims)
	                .setSubject(username)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
	                .signWith(getKey(), SignatureAlgorithm.HS256)
	                .compact();
	        System.out.println("Generated Token: " + token);
	        System.out.println("Signing Key: " + Base64.getEncoder().encodeToString(getKey().getEncoded()));
return token;
	    }

	    private static SecretKey getKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(heresecretkey);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

	    public String extractUsername(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(getKey())
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	        return claims.getSubject();
	    }

	    public boolean validateToken(String token, UserDetails userDetails) {
	        Claims claims;
	        try {
	            claims = Jwts.parser()
	                    .setSigningKey(getKey())
	                    .build()
	                    .parseClaimsJws(token)
	                    .getBody();
	        } catch (Exception e) {
	        	System.out.println("jwtfaild");
	            return false; // Token is invalid
	        }
	        return claims.getExpiration().after(new Date()) &&
	                userDetails.getUsername().equals(claims.getSubject());
	    }
	}


