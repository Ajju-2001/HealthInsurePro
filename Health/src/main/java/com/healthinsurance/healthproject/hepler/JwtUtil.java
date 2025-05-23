package com.healthinsurance.healthproject.hepler;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.healthinsurance.healthproject.personalentities.Users;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	//Used to sign and validate JWTs
	private static final String SECRET = "1234567890123456789012345678901234567890123456789012345678901234";

	
    private Key SECRET_KEY;  
	
	private final long expirationTimeMs = 1000 * 60 * 60 * 24; 
	 
	//This method runs once automatically after the Spring bean is constructed.
	@PostConstruct
    public void init() {
		if (SECRET.length() < 64) {
            throw new IllegalArgumentException("Secret key must be at least 64 characters");
        }
		//Converts the SECRET string into a Key object using UTF-8 encoding. This key is later used for signing JWTs.
		this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));  
    } 

    public String generateToken(Users user) {
        
    	Map<String, Object> claims = new HashMap<>();  
    	claims.put("userId", user.getUserId()); 
    	claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
         

        return Jwts.builder()
        		.setClaims(claims)       
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(SECRET_KEY) 
                .compact(); //It returns the JWT string.
    } 
    
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    } 
    
 
    public String extractUsername(String token) {
    	return extractAllClaims(token).getSubject(); 
    }
    
    public Integer extractUserId(String token) {
    	 return (Integer) extractAllClaims(token).get("userId");
    } 
    
    public String extractEmail(String token) {
    	return (String) extractAllClaims(token).get("email");
    }

    public String extractRole(String token) {
    	return (String) extractAllClaims(token).get("role");
    }

//    public boolean validateToken(String token,Integer userId, String username, String email, String role) {
//    	Integer extactInteger = extractUserId(token);
//    	String extractedUsername = extractUsername(token);
//        String extractedEmail = extractEmail(token);  
//        String extractedRole = extractRole(token);  
//        return (
//        	userId.equals(extactInteger) &&
//            username.equals(extractedUsername) && 
//            email.equals(extractedEmail) &&
//            role.equals(extractedRole) &&
//            !isTokenExpired(token)  
//        ); 
//    }
 

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

//    private Date extractExpiration(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//    }
}
