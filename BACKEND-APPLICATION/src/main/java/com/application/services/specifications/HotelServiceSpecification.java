package com.application.services.specifications;

import com.application.entities.Hotel;

import java.util.List;

public interface HotelServiceSpecification {
    Hotel addHotel(Hotel hotel);
    Hotel updateHotel(Hotel hotel);
    Hotel getHotelById(String id);
    List<Hotel>  getHotels();
    List<Hotel> findHotelsByOwnerId(Integer ownerId);
    void deleteHotel();
}
