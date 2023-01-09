package com.application.controllers;

import com.application.entities.Client;
import com.application.entities.Reservation;
import com.application.entities.Room;
import com.application.entities.User;
import com.application.services.specifications.ReservationServiceSpecification;
import com.application.services.specifications.RoomServiceSpecification;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationServiceSpecification reservationServiceBean;
    private final UserServiceSpecification userServiceBean;
    private final RoomServiceSpecification roomServiceBean;

    @Autowired
    public ReservationController(
            ReservationServiceSpecification reservationServiceBean,
            UserServiceSpecification userServiceBean,
            RoomServiceSpecification roomServiceBean) {
        this.reservationServiceBean = reservationServiceBean;
        this.userServiceBean = userServiceBean;
        this.roomServiceBean = roomServiceBean;
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationServiceBean.getReservations();
        return new ResponseEntity<>(reservations, OK);
    }

    @GetMapping("/client/reservations/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable Integer id) {
        List<Reservation> reservations = reservationServiceBean.getReservationsByClientId(id);
        return new ResponseEntity<>(reservations, OK);
    }

    @GetMapping("/reservation/add/{client_id}/{room_id}")
    public void addReservation(@PathVariable("client_id") Integer client_id, @PathVariable("room_id") Integer room_id) {
        User client = userServiceBean.findUserById(client_id);
        Room room = roomServiceBean.getRoomById(room_id);
        Reservation reservation = new Reservation();
        reservation.setClient((Client) client);
        reservation.setRoom(room);
        reservation.setApproved(false);
        reservationServiceBean.addReservation(reservation);
    }

}
