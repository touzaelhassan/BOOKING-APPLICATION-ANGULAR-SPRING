package com.application.services.implementations;

import com.application.entities.Hotel;
import com.application.repositories.HotelRepository;
import com.application.services.specifications.HotelServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("hotelServiceBean")
public class HotelServiceImplementation implements HotelServiceSpecification {

    private final HotelRepository hotelRepositoryBean;

    @Autowired
    public HotelServiceImplementation(HotelRepository hotelRepositoryBean) { this.hotelRepositoryBean = hotelRepositoryBean; }

    @Override
    public Hotel addHotel(Hotel hotel) { return hotelRepositoryBean.save(hotel); }
    @Override
    public Hotel updateHotel(Hotel hotel) { return null; }
    @Override
    public Hotel getHotelById(String id) { return null; }
    @Override
    public List<Hotel> getHotels() { return hotelRepositoryBean.findAll(); }
    @Override
    public List<Hotel> findHotelsByOwnerId(Integer ownerId) { return hotelRepositoryBean.findByOwnerId(ownerId); }
    @Override
    public void deleteHotel() { }

}
