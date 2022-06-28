package com.example.ASMapi.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TokenManager {

    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstants.EXPIRATION_TIME))
                .signWith(JwtConstants.key)
                .compact();
    }

    public boolean validToken(String token) {
        return getUsernameFromToken(token) != null && isValid(token);
    }

    public String getUsernameFromToken(String token){
        return getClaims(token).getSubject();
    }

    public boolean isValid(String token){
        return getClaims(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }

    private Claims getClaims(String token){
        return Jwts.parser().setSigningKey(JwtConstants.key)
                .parseClaimsJws(token).getBody();
    }

}
