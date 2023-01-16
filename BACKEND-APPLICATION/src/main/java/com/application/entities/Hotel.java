package com.application.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hotels")
@Setter
@Getter
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean isAvailable;
    private boolean isApproved;
    @ManyToOne
    private Owner owner;
    @ManyToOne
    private City city;
    @OneToMany(mappedBy = "hotel")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Room> rooms = new ArrayList<>();

    public Hotel() { }

}
