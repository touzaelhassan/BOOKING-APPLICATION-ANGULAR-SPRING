package com.application.services.implementations;

import com.application.entities.Hotel;
import com.application.entities.Room;
import com.application.repositories.RoomRepository;
import com.application.services.specifications.HotelServiceSpecification;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service("roomServiceBean")
public class RoomServiceImplementation implements RoomServiceSpecification {

    private RoomRepository roomRepositoryBean;
    private HotelServiceSpecification hotelServiceBean;

    @Autowired
    public RoomServiceImplementation(RoomRepository roomRepositoryBean) { this.roomRepositoryBean = roomRepositoryBean; }

    @Override
    public Room addRoom(Room room) { return roomRepositoryBean.save(room); }

    @Override
    public Room addRoom(Integer hotelId, String name, String description, boolean isAvailable, MultipartFile roomImage) throws IOException {
        Hotel hotel = hotelServiceBean.getHotelById(hotelId);
        Room room = new Room();
        room.setName(name);
        room.setDescription(description);
        room.setAvailable(isAvailable);
        room.setHotel(hotel);
        room.setImageUrl(getTemporaryProfileImageUrl(name));
        roomRepositoryBean.save(room);
        this.saveRoomImage(room, roomImage);
        return  room;
    }

    @Override
    public Room updateRoom(Room room) { return null; }
    @Override
    public Room getRoomById(Integer id) { return roomRepositoryBean.findById(id).orElse(null); }
    @Override
    public List<Room> getRooms() { return roomRepositoryBean.findAll(); }
    @Override
    public List<Room> getRoomsByHotelId(Integer hotelId) { return roomRepositoryBean.findByHotelId(hotelId); }
    @Override
    public void deleteRoom() { }

    private String getTemporaryProfileImageUrl(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_ROOM_IMAGE_PATH + name).toUriString();
    }

    private void saveRoomImage(Room room, MultipartFile roomImage) throws IOException {
        if (roomImage != null) {
            Path userFolder = Paths.get(ROOM_FOLDER + room.getName()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + room.getName()+ DOT + JPG_EXTENSION));
            Files.copy(roomImage.getInputStream(), userFolder.resolve(room.getName()  + DOT + JPG_EXTENSION), REPLACE_EXISTING);
            room.setImageUrl(this.setRoomImageUrl(room.getName()));
            roomRepositoryBean.save(room);
        }
    }

    private String setRoomImageUrl(String name) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path( ROOM_IMAGE_PATH + name + FORWARD_SLASH + name + DOT + JPG_EXTENSION).toUriString();
    }

}
