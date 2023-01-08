package com.application.repositories;

import com.application.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("hotelRepositoryBean")
public interface HotelRepository extends JpaRepository<Hotel, Integer> {

    @Query("SELECT h FROM Hotel h WHERE h.owner.id = :ownerId")
    List<Hotel> findByOwnerId(@Param("ownerId") Integer ownerId);

}
