package com.application.enums;

import static com.application.constants.AuthorityConstants.*;

public enum Role {

    ROLE_USER(USER_AUTHORITIES),
    ROLE_CLIENT(CLIENT_AUTHORITIES),
    ROLE_OWNER(OWNER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authorities;

    Role(String... authorities){ this.authorities = authorities; }

    public String[] getAuthorities(){ return this.authorities; }

}
