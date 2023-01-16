package com.application.services.implementations;

import com.application.entities.Hotel;
import com.application.entities.Owner;
import com.application.repositories.HotelRepository;
import com.application.services.specifications.HotelServiceSpecification;
import com.application.services.specifications.UserServiceSpecification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.application.constants.FileConstants.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service("hotelServiceBean")
public class HotelServiceImplementation implements HotelServiceSpecification {

    private final UserServiceSpecification userServiceBean;
    private final HotelRepository hotelRepositoryBean;

    public HotelServiceImplementation(UserServiceSpecification userServiceBean, HotelRepository hotelRepositoryBean) {
        this.userServiceBean = userServiceBean;
        this.hotelRepositoryBean = hotelRepositoryBean;
    }

    @Override
    public Hotel addHotel(Hotel hotel) { return hotelRepositoryBean.save(hotel); }

    @Override
    public Hotel addHotel(String name, String description, String ownerUsername, boolean isAvailable, boolean isApproved, MultipartFile hotelImage) throws IOException {

        Owner owner = (Owner) userServiceBean.findUserByUsername(ownerUsername);
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setAvailable(isAvailable);
        hotel.setApproved(isApproved);
        hotel.setOwner(owner);
        hotel.setImageUrl(getTemporaryProfileImageUrl(name));
        hotelRepositoryBean.save(hotel);
        this.saveHotelImage(hotel, hotelImage);
        return  hotel;
    }


    @Override
    public Hotel updateHotel(Hotel hotel) { return null; }

    @Override
    public Hotel updateHotel(Integer id, String name, String description, String ownerUsername, boolean isAvailable, boolean isApproved, MultipartFile hotelImage) throws IOException {
        Owner owner = (Owner) userServiceBean.findUserByUsername(ownerUsername);
        Hotel hotel = hotelRepositoryBean.findById(id).orElse(null);
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setAvailable(isAvailable);
        hotel.setApproved(isApproved);
        hotel.setOwner(owner);
        hotel.setImageUrl(getTemporaryProfileImageUrl(name));
        hotelRepositoryBean.save(hotel);
        this.saveHotelImage(hotel, hotelImage);
        return  hotel;

    }

    @Override
    public Hotel getHotelById(Integer id) { return hotelRepositoryBean.findById(id).orElse(null); }
    @Override
    public List<Hotel> getHotels() { return hotelRepositoryBean.findAll(); }
    @Override
    public List<Hotel> findHotelsByOwnerId(Integer ownerId) { return hotelRepositoryBean.findByOwnerId(ownerId); }
    @Override
    public void deleteHotel(Integer id) { hotelRepositoryBean.deleteById(id);}


    private String getTemporaryProfileImageUrl(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_HOTEL_IMAGE_PATH + name).toUriString();
    }

    private void saveHotelImage(Hotel hotel, MultipartFile hotelImage) throws IOException{
        if (hotelImage != null) {
            Path userFolder = Paths.get(USER_FOLDER + hotel.getName()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + hotel.getName()+ DOT + JPG_EXTENSION));
            Files.copy(hotelImage.getInputStream(), userFolder.resolve(hotel.getName()  + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            hotel.setImageUrl(this.setHotelImageUrl(hotel.getName()));
            hotelRepositoryBean.save(hotel);
        }
    }

    private String setHotelImageUrl(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path( HOTEL_IMAGE_PATH + name + FORWARD_SLASH + name + DOT + JPG_EXTENSION).toUriString();
    }

}
