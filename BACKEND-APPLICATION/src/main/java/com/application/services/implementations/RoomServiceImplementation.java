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
    public Room getRoomById(Integer id) { return roomRepositoryBean.findById(id).orElse(null); }
    @Override
    public List<Room> getRooms() { return roomRepositoryBean.findAll(); }
    @Override
    public List<Room> getRoomsByHotelId(Integer hotelId) { return roomRepositoryBean.findByHotelId(hotelId); }
    @Override
    public void deleteRoom() { }

}
