package com.application.controllers;

import com.application.entities.Hotel;
import com.application.entities.Room;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api")
public class RoomController {

    private final RoomServiceSpecification roomServiceBean;

    @Autowired
    public RoomController(RoomServiceSpecification roomServiceBean) { this.roomServiceBean = roomServiceBean; }

    @PostMapping("/room/add")
    public ResponseEntity<Room> addHotel(
            @RequestParam("hotelId") Integer hotelId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("isAvailable") String isAvailable,
            @RequestParam(value = "roomImage", required = false) MultipartFile roomImage
            ) {
          Room room = roomServiceBean.addRoom(hotelId, name, description, Boolean.parseBoolean(isAvailable), roomImage) ;
          return new ResponseEntity<>(room, OK);
    }

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

    @GetMapping("/room/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Integer id) {
        Room room = roomServiceBean.getRoomById(id);
        return new ResponseEntity<>(room, OK);
    }
}
