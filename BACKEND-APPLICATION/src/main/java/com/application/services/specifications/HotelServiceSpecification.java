package com.application.services.specifications;

import com.application.entities.Hotel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface HotelServiceSpecification {
    Hotel addHotel(Hotel hotel);
    Hotel addHotel(String name, String description, String city, String ownerUsername, boolean isAvailable, boolean isApproved, MultipartFile hotelImage) throws IOException;
    Hotel updateHotel(Hotel hotel);
    Hotel getHotelById(String id);
    List<Hotel>  getHotels();
    List<Hotel> findHotelsByOwnerId(Integer ownerId);
    void deleteHotel();

}
