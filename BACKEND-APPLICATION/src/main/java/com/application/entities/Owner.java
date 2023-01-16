package com.application.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner extends User{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Hotel> hotels = new ArrayList<>();

    public Owner() {}
    public Owner(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
    }
    public Owner(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities, List<Hotel> hotels) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
        this.hotels = hotels;
    }

}
