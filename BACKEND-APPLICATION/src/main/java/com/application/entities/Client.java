package com.application.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Reservation> reservations = new ArrayList<>();

    public Client() {}

    public Client(String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, String[] authorities) {
        super(userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role, authorities);
    }

    public Client(Integer id, String userId, String firstname, String lastname, String username, String email, String password, String profileImageUrl, Date lastLoginDate, Date lastLoginDateDisplay, Date joinDate, boolean isActive, boolean isNotLocked, String role, List<Reservation> reservations) {
        super(id, userId, firstname, lastname, username, email, password, profileImageUrl, lastLoginDate, lastLoginDateDisplay, joinDate, isActive, isNotLocked, role);
        this.reservations = reservations;
    }

    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
    public List<Reservation> getReservations() { return reservations; }

}
