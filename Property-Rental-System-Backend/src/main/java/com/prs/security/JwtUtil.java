//package com.prs.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.security.NoSuchAlgorithmException;
//import java.util.Base64;
//import java.util.Date;
//import java.util.function.Function;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//@Component
//public class JwtUtil {
//
//	// Secret key as a base64-encoded string
//	@Value("${jwt.secret}")
//	private String SECRET_KEY;
//
//	public JwtUtil(){
//    	try {
//	    	KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256") ;
//	    	SecretKey sk = keyGen.generateKey();
//	    	SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
//    	}
//    	catch (NoSuchAlgorithmException e) {
//    	throw new RuntimeException(e);
//    	}
//	}
//    
//    // Generate a signing key from the secret key
//	private Key getSigningKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}
//
//	// Generate a token with the user's email and role
////	public String generateToken(String email, String role) {
////		return Jwts.builder().setSubject(email).claim("role", role).setIssuedAt(new Date())
////				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
////				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
////	}
//	public String generateToken(String email, String role) {
//	    long expirationTimeMillis = System.currentTimeMillis() + 86400000*999; // 10 hours
//	    Date expirationDate = new Date(expirationTimeMillis);
//	    
//	    // Log expiration time to check if it's being calculated correctly
//	    System.out.println("Expiration Time: " + expirationDate);
//
//	    return Jwts.builder()
//	        .setSubject(email)
//	        .claim("role", role)
//	        .setIssuedAt(new Date())  // Current time
//	        .setExpiration(expirationDate)  // Expiration time calculated above
//	        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//	        .compact();
//	}
//
//	// Extract the email (subject) from the token
//	public String extractEmail(String token) {
//		return extractClaim(token, Claims::getSubject);
//	}
//
//	// Extract role from the token
//	public String extractRole(String token) {
//		Claims claims = extractAllClaims(token);
//		return claims.get("role", String.class);
//	}
//
//	// Extract any claim from the token
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimsResolver.apply(claims);
//	}
//
//	private Claims extractAllClaims(String token) {
//		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
//	}
//
//	// Check if the token has expired
//	private Boolean isTokenExpired(String token) {
//		return extractClaim(token, Claims::getExpiration).before(new Date());
//	}
//
//	// Validate the token
//	public Boolean validateToken(String token, String email) {
//		final String extractedEmail = extractEmail(token);
//		return (extractedEmail.equals(email) && !isTokenExpired(token));
//	}
//}

package com.prs.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public Boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }
}
