package com.application.controllers;

import com.application.entities.Hotel;
import com.application.entities.Room;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomServiceSpecification roomServiceBean;

    @Autowired
    public RoomController(RoomServiceSpecification roomServiceBean) { this.roomServiceBean = roomServiceBean; }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = roomServiceBean.getRooms();
        return new ResponseEntity<>(rooms, OK);
    }

    @GetMapping("/hotel/rooms/{id}")
    public ResponseEntity<List<Room>> getRoomsByHotelId(@PathVariable Integer id) {
        List<Room> rooms = roomServiceBean.getRoomsByHotelId(id);
        return new ResponseEntity<>(rooms, OK);
    }

}
