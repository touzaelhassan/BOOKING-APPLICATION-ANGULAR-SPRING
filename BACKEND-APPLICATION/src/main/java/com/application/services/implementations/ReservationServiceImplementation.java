package com.application.services.implementations;

import com.application.entities.Reservation;
import com.application.repositories.ReservationRepository;
import com.application.services.specifications.ReservationServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("reservationServiceBean")
public class ReservationServiceImplementation implements ReservationServiceSpecification {

    private final ReservationRepository reservationRepositoryBean;

    @Autowired
    public ReservationServiceImplementation(ReservationRepository reservationRepositoryBean) { this.reservationRepositoryBean = reservationRepositoryBean; }

    @Override
    public Reservation addReservation(Reservation reservation) { return reservationRepositoryBean.save(reservation); }

}
