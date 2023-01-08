package com.application.controllers;

import com.application.entities.Hotel;
import com.application.services.specifications.HotelServiceSpecification;
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
public class HotelController {

    private final HotelServiceSpecification hotelServiceBean;

    @Autowired
    public HotelController(HotelServiceSpecification hotelServiceBean) { this.hotelServiceBean = hotelServiceBean; }

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

}
