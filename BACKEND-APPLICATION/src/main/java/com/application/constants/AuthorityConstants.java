package com.application.constants;

public class AuthorityConstants {
    public static final String[] USER_AUTHORITIES = { };
    public static final String[] CLIENT_AUTHORITIES = { };
    public static final String[] OWNER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete" };
}
