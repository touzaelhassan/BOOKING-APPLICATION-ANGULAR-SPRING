package com.application.controllers;

import com.application.entities.Reservation;
import com.application.services.specifications.ReservationServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationServiceSpecification reservationServiceBean;
    @Autowired
    public ReservationController(ReservationServiceSpecification reservationServiceBean) { this.reservationServiceBean = reservationServiceBean; }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationServiceBean.getReservations();
        return new ResponseEntity<>(reservations, OK);
    }
}
