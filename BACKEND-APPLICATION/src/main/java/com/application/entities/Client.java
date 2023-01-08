package com.application.entities;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private List<Reservation> reservations = new ArrayList<>();

    public Client() {}
    public Client(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
    }
    public Client(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities, List<Reservation> reservations) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
        this.reservations = reservations;
    }

}
