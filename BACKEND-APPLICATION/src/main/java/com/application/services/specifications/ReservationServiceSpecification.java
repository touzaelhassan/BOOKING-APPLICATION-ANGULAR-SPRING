package com.application.services.specifications;

import com.application.entities.Reservation;

import java.util.List;

public interface ReservationServiceSpecification {
    Reservation addReservation(Reservation reservation);
    Reservation addReservation(Integer clientId, Integer roomId, String checking, String checkout);
    Reservation getReservationById(Integer id);
    Reservation changeReservationStatus(Integer id);
    List<Reservation> getReservations();
    List<Reservation> getReservationsByClientId(Integer clientId);
    List<Reservation> findReservationByOwnerId(Integer ownerId);
    void deleteReservation(Integer id);
}
