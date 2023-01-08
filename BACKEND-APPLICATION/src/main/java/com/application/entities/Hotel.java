package com.application.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean isAvailable;
    private boolean isApproved;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();

    public Hotel() { }

    public Hotel(String name, Owner owner, City city, List<Room> rooms) {
        this.name = name;
        this.owner = owner;
        this.city = city;
        this.rooms = rooms;
    }

    public Hotel(Integer id, String name, Owner owner, City city, List<Room> rooms) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.city = city;
        this.rooms = rooms;
    }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setOwner(Owner owner) { this.owner = owner; }
    public void setCity(City city) { this.city = city; }
    public void setRooms(List<Room> rooms) { this.rooms = rooms; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Owner getOwner() { return owner; }
    public City getCity() { return city; }
    public List<Room> getRooms() { return rooms; }
    public boolean isAvailable() { return isAvailable; }
    public boolean isApproved() { return isApproved; }

}
