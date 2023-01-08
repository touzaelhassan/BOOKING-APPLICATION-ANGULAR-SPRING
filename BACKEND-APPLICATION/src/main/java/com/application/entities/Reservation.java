package com.application.entities;

import javax.persistence.*;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Room room;
    boolean isApproved;

    public Reservation() { }

    public Reservation(Client client, Room room) {
        this.client = client;
        this.room = room;
    }

    public Reservation(Integer id, Client client, Room room) {
        this.id = id;
        this.client = client;
        this.room = room;
    }

    public void setId(Integer id) { this.id = id; }
    public void setClient(Client client) { this.client = client; }
    public void setRoom(Room room) { this.room = room; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public Integer getId() { return id; }
    public Client getClient() { return client; }
    public Room getRoom() { return room; }
    public boolean isApproved() { return isApproved; }

}
