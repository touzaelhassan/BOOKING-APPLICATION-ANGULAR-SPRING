package com.application.controllers;

import com.application.entities.Hotel;
import com.application.entities.User;
import com.application.exceptions.classes.EmailExistException;
import com.application.exceptions.classes.NotAnImageFileException;
import com.application.exceptions.classes.UserNotFoundException;
import com.application.exceptions.classes.UsernameExistException;
import com.application.services.specifications.HotelServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.application.controllers.constants.FileConstants.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelServiceSpecification hotelServiceBean;

    @Autowired
    public HotelController(HotelServiceSpecification hotelServiceBean) { this.hotelServiceBean = hotelServiceBean; }


    @PostMapping("/hotel/add")
    public ResponseEntity<Hotel> addHotel(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("city") String city,
            @RequestParam("ownerUsername") String ownerUsername,
            @RequestParam("isAvailable") String isAvailable,
            @RequestParam("isApproved") String isApproved,
            @RequestParam(value = "hotelImage", required = false) MultipartFile hotelImage
    ) throws IOException {

        Hotel hotel = hotelServiceBean.addHotel(name, description, city, ownerUsername, Boolean.parseBoolean(isAvailable), Boolean.parseBoolean(isApproved), hotelImage);
        return new ResponseEntity<>(hotel, OK);

    }

    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getHotels() {
        List<Hotel> hotels = hotelServiceBean.getHotels();
        return new ResponseEntity<>(hotels, OK);
    }

    @GetMapping("/owner/hotels/{id}")
    public ResponseEntity<List<Hotel>> getHotelsByOwnerId(@PathVariable Integer id) {
        List<Hotel> hotels = hotelServiceBean.findHotelsByOwnerId(id);
        return new ResponseEntity<>(hotels, OK);
    }

    @GetMapping(path = "/hotel/image/{username}/{fileName}", produces = IMAGE_JPEG_VALUE)
    public byte[] getProfileImage(@PathVariable("username") String username, @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(USER_FOLDER + username + FORWARD_SLASH + fileName));
    }

}
