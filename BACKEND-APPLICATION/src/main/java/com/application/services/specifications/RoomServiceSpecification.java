package com.application.services.specifications;

import com.application.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface RoomServiceSpecification {
    Room addRoom(Room room);
    Room addRoom(Integer hotelId, String name, String description, boolean isAvailable, MultipartFile roomImage) throws IOException;
    Room updateRoom(Room room);
    Room getRoomById(Integer id);
    List<Room> getRooms();
    List<Room> getRoomsByHotelId(Integer hotelId);
    void deleteRoom();
}
