package com.akohsin.photostorage.security;

/**
 * AKN 3/9/18
 */

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 10 * 24 * 60 * 60 * 1000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer:";
    public static final String HEADER_STRING = "Authorization";
    public static final String EXPIRATION = "Expires_at";
}
