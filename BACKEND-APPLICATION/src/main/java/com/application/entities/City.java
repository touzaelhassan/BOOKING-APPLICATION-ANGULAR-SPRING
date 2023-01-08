package com.application.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "city")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Hotel> hotels = new ArrayList<>();

    public City() { }

    public City(String name, List<Hotel> hotels) {
        this.name = name;
        this.hotels = hotels;
    }

    public City(Integer id, String name, List<Hotel> hotels) {
        this.id = id;
        this.name = name;
        this.hotels = hotels;
    }

    public void setId(Integer id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHotels(List<Hotel> hotels) { this.hotels = hotels;}

    public Integer getId() { return id; }
    public String getName() { return name; }
    public List<Hotel> getHotels() { return hotels; }

}
