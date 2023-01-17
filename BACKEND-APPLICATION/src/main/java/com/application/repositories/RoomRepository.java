package com.application.repositories;

import com.application.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roomRepositoryBean")
public interface RoomRepository extends JpaRepository<Room, Integer> {

    @Query("SELECT room FROM Room room WHERE room.hotel.id = :hotelId")
    List<Room> findByHotelId(@Param("hotelId") Integer hotelId);

    @Query("SELECT room FROM Room room WHERE room.hotel.id = :ownerId")
    List<Room> findByOwnerId(@Param("ownerId") Integer hotelId);
}
