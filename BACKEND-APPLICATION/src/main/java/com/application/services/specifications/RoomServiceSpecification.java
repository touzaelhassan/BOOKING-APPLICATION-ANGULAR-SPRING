package com.application.services.specifications;

import com.application.entities.Hotel;
import com.application.entities.Room;

import java.util.List;

public interface RoomServiceSpecification {
    Room addRoom(Room room);
    Room updateRoom(Room room);
    Room getRoomById(String id);
    List<Room> getRooms ();
    void deleteRoom();
}
