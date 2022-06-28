package com.example.ASMapi.security.utils;


import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class JwtConstants {

    static final Key key = Keys.hmacShaKeyFor("securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecure".getBytes());
    public static final long EXPIRATION_TIME = 5 * 24 * 60 * 60 * 1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";

}
