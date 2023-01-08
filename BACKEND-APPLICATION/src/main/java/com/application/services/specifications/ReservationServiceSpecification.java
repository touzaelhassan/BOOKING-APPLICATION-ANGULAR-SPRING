package com.application.services.specifications;

import com.application.entities.Reservation;

import java.util.List;

public interface ReservationServiceSpecification {
    Reservation addReservation(Reservation reservation);
    List<Reservation> getReservations();
}
