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
    @ManyToOne
    private Owner owner;
    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();

}
