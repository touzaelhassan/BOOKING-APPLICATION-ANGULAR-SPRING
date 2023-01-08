package com.application.repositories;

import com.application.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roomRepositoryBean")
public interface RoomRepository extends JpaRepository<Room, Integer> { }
