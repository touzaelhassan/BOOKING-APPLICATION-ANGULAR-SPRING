package com.application.controllers;

import com.application.entities.Hotel;
import com.application.services.specifications.HotelServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<Hotel>> getUsers() {
        List<Hotel> hotels = hotelServiceBean.getHotels();
        return new ResponseEntity<>(hotels, OK);
    }

}
