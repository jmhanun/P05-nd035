package com.example.demo.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String SECRET = "mysecretkey";
    public static final String TOKEN_PREFIX = "jmhanun ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/api/user/create";
}