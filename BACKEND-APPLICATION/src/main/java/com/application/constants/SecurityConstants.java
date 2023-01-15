package com.application.constants;

public class SecurityConstants {

    public static final long EXPIRATION_TIME = 432_000_000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String GET_ARRAYS_LLC = "Get Arrays, LLC";
    public static final String GET_ARRAYS_ADMINISTRATION = "User Management Portal";
    public static final String AUTHORITIES = "authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this endpoint";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this endpoint";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = { "/api/authentication/register", "/api/authentication/login", "/api/user/image/**", "/api/hotels", "/api/rooms", "/api/room/**"};
    // public static final String[] PUBLIC_URLS = { "**" };

}