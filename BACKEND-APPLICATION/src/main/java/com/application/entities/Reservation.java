package com.application.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String checking;
    private String checkout;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Room room;
    boolean isApproved;

    public Reservation() { }

}
