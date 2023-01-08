package com.application.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "admins")
public class Admin extends User{

    public Admin() {}
    public Admin(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
    }

}
