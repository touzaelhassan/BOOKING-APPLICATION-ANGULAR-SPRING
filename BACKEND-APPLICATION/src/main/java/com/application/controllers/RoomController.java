package com.application.controllers;

import com.application.classes.HttpResponse;
import com.application.entities.Hotel;
import com.application.entities.Room;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.application.constants.FileConstants.FORWARD_SLASH;
import static com.application.constants.FileConstants.USER_FOLDER;
import static com.application.controllers.HotelController.HOTEL_DELETED_SUCCESSFULLY;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/api")
public class RoomController {

    public static final String ROOM_DELETED_SUCCESSFULLY = "Room Deleted Successfully !!.";
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
            ) throws IOException {
          Room room = roomServiceBean.addRoom(hotelId, name, description, Boolean.parseBoolean(isAvailable), roomImage) ;
          return new ResponseEntity<>(room, OK);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms() {
        List<Room> rooms = roomServiceBean.getRooms();
        return new ResponseEntity<>(rooms, OK);
    }

    @GetMapping("/rooms/owner/{id}")
    public ResponseEntity<List<Room>> getRoomsByOwnerId(@PathVariable Integer id) {
        System.out.println(this);
        System.out.println("getRoomsByOwnerId");
        List<Room> rooms = roomServiceBean.getRoomsByOwnerId(id);
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

    @GetMapping(path = "/room/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

    @DeleteMapping("/room/delete/{id}")
    public ResponseEntity<HttpResponse> deleteHotel(@PathVariable("id") Integer id) throws IOException {
        roomServiceBean.deleteRoom(id);
        return response(OK, ROOM_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(), message), httpStatus);
    }

}
