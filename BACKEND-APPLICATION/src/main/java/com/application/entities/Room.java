package com.application.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean isAvailable;
    @ManyToOne
    private Hotel hotel;
    @OneToMany(mappedBy = "room")
    private List<Reservation> reservations = new ArrayList<>();

    public Room() { }

    public Room(String name, boolean isAvailable, Hotel hotel, List<Reservation> reservations) {
        this.name = name;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
        this.reservations = reservations;
    }

    public Room(Integer id, String name, boolean isAvailable, Hotel hotel, List<Reservation> reservations) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
        this.hotel = hotel;
        this.reservations = reservations;
    }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setHotel(Hotel hotel) { this.hotel = hotel; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }
    public Hotel getHotel() { return hotel; }
    public List<Reservation> getReservations() { return reservations; }

}
