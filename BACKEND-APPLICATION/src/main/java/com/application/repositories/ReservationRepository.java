package com.application.repositories;

import com.application.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reservationRepositoryBean")
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query("SELECT s FROM Reservation s WHERE s.client.id = :clientId")
    List<Reservation> findByClientId(@Param("clientId") Integer clientId);

}
