package org.linkedin.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static final String SECRET_KEY = "UiWLQvbN4uQrBHcfAGKqTizE5o7MG+jhXhkNy/knuj4=";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    public static String generateToken(String username) throws UnsupportedEncodingException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", username);
        claims.put("iat", new Date(System.currentTimeMillis()));
        claims.put("exp", new Date(System.currentTimeMillis() + EXPIRATION_TIME));

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes("UTF-8"))
                .compact();
    }


    public static String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }
}
//public ResponseEntity<String> getProtectedResource(@RequestHeader("Authorization") String bearerToken) {
//    // Validate the JWT token
//    if (JwtUtil.validateToken(bearerToken.substring(7))) {
//        String username = JwtUtil.getUsernameFromToken(bearerToken.substring(7));
//        return ResponseEntity.ok("Hello, " + username + "!");
//    } else {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
//    }
//}