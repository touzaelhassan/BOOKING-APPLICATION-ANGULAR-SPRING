package com.application.repositories;

import com.application.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("reservationRepositoryBean")
public interface ReservationRepository extends JpaRepository<Reservation, Integer> { }
