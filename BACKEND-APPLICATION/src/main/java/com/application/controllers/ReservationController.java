package com.application.controllers;

import com.application.classes.HttpResponse;
import com.application.entities.*;
import com.application.services.specifications.ReservationServiceSpecification;
import com.application.services.specifications.RoomServiceSpecification;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class ReservationController {

    public static final String RESERVATION_DELETED_SUCCESSFULLY = "Room Deleted Successfully !!.";
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

    @PostMapping("/reservation/add")
    public ResponseEntity<Reservation> addReservation(
            @RequestParam("clientId") Integer clientId,
            @RequestParam("roomId") Integer roomId,
            @RequestParam("checking") String checking,
            @RequestParam("checkout") String checkout

    )  {

        Reservation reservation = reservationServiceBean.addReservation(clientId, roomId, checking, checkout);

        return new ResponseEntity<>(new Reservation(), OK);

    }

    @GetMapping("/reservation/add/{client_id}/{room_id}")
    //@PreAuthorize("hasAnyAuthority('reservation:create')")
    public void addReservation(@PathVariable("client_id") Integer client_id, @PathVariable("room_id") Integer room_id) {
        User client = userServiceBean.findUserById(client_id);
        Room room = roomServiceBean.getRoomById(room_id);
        Reservation reservation = new Reservation();
        reservation.setClient((Client) client);
        reservation.setRoom(room);
        reservation.setApproved(false);
        reservationServiceBean.addReservation(reservation);
    }

    @GetMapping("/reservation/update/{id}")
    public ResponseEntity<Reservation> changeReservationStatus(@PathVariable("id") Integer id) {
        Reservation reservation = reservationServiceBean.changeReservationStatus(id);
        return new ResponseEntity<>(reservation, OK);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getReservations() {
        List<Reservation> reservations = reservationServiceBean.getReservations();
        return new ResponseEntity<>(reservations, OK);
    }

    @GetMapping("/owner/reservations/{ownerId}")
    public ResponseEntity<List<Reservation>> getReservationsByOwnerId(@PathVariable Integer ownerId) {
        List<Reservation> reservations = reservationServiceBean.findReservationByOwnerId(ownerId);
        return new ResponseEntity<>(reservations, OK);
    }

    @GetMapping("/client/reservations/{clientId}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable Integer clientId) {
        List<Reservation> reservations = reservationServiceBean.getReservationsByClientId(clientId);
        return new ResponseEntity<>(reservations, OK);
    }

    @DeleteMapping("/reservation/delete/{id}")
    public ResponseEntity<HttpResponse> deleteRoom(@PathVariable("id") Integer id) {
        reservationServiceBean.deleteReservation(id);
        return response(OK, RESERVATION_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }
}
