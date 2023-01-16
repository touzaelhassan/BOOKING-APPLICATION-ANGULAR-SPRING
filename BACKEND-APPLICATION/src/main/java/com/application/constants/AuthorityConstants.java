package com.application.constants;

public class AuthorityConstants {
    public static final String[] USER_AUTHORITIES = { };
    public static final String[] CLIENT_AUTHORITIES = { "reservation:create", "reservation:read", "reservation:update", "reservation:delete"};
    public static final String[] OWNER_AUTHORITIES = {"user:read", "hotel:create", "hotel:read", "hotel:update", "hotel:delete", "room:create", "room:read", "room:update", "room:delete", "reservation:read", "reservation:update", "reservation:delete"};
    public static final String[] ADMIN_AUTHORITIES = {"user:create", "user:read", "user:update"};
    public static final String[] SUPER_ADMIN_AUTHORITIES = {"user:create", "user:read", "user:update", "user:delete", "hotel:update", "hotel:delete", "reservation:update", "reservation:delete"};
}
