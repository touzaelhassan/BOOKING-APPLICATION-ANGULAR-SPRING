package com.application.services.implementations;

import com.application.entities.Room;
import com.application.repositories.RoomRepository;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("roomServiceBean")
public class RoomServiceImplementation implements RoomServiceSpecification {

    private RoomRepository roomRepositoryBean;

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepositoryBean) { this.roomRepositoryBean = roomRepositoryBean; }

    @Override
    public Room addRoom(Room room) { return roomRepositoryBean.save(room); }
    @Override
    public Room updateRoom(Room room) { return null; }
    @Override
    public Room getRoomById(String id) { return null; }
    @Override
    public List<Room> getRooms() { return null; }
    @Override
    public void deleteRoom() { }

}
