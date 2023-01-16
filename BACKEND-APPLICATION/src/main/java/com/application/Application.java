package com.application;

import com.application.entities.*;
import com.application.repositories.UserRepository;
import com.application.services.specifications.HotelServiceSpecification;
import com.application.services.specifications.RoomServiceSpecification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static com.application.controllers.constants.FileConstants.USER_FOLDER;
import static com.application.enums.Role.*;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        new File(USER_FOLDER).mkdirs();
    }

    @Bean
    CommandLineRunner run(
            UserRepository userRepositoryBean,
            HotelServiceSpecification hotelServiceBean,
            RoomServiceSpecification roomServiceBean,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ){

        return args -> {
       /*
            Admin admin = new Admin();
            admin.setUserId("534376");
            admin.setFirstname("El Hassan");
            admin.setLastname("Touza");
            admin.setUsername("hassan");
            admin.setEmail("hassan@gmail.com");
            admin.setJoinDate(new Date());
            admin.setPassword(bCryptPasswordEncoder.encode("123456"));
            admin.setActive(true);
            admin.setNotLocked(true);
            admin.setRole(ROLE_SUPER_ADMIN.name());
            admin.setAuthorities(ROLE_SUPER_ADMIN.getAuthorities());
            userRepositoryBean.save(admin);

            Owner owner1 = new Owner();
            owner1.setUserId("534345");
            owner1.setFirstname("Karim");
            owner1.setLastname("Lamomov");
            owner1.setUsername("karim");
            owner1.setEmail("karim@gmail.com");
            owner1.setJoinDate(new Date());
            owner1.setPassword(bCryptPasswordEncoder.encode("123456"));
            owner1.setActive(true);
            owner1.setNotLocked(true);
            owner1.setRole(ROLE_OWNER.name());
            owner1.setAuthorities(ROLE_OWNER.getAuthorities());
            userRepositoryBean.save(owner1);

            Owner owner2 = new Owner();
            owner2.setUserId("534345");
            owner2.setFirstname("Amine");
            owner2.setLastname("kalalm");
            owner2.setUsername("amine");
            owner2.setEmail("amine@gmail.com");
            owner2.setJoinDate(new Date());
            owner2.setPassword(bCryptPasswordEncoder.encode("123456"));
            owner2.setActive(true);
            owner2.setNotLocked(true);
            owner2.setRole(ROLE_OWNER.name());
            owner2.setAuthorities(ROLE_OWNER.getAuthorities());
            userRepositoryBean.save(owner2);

            Client client1 = new Client();
            client1.setUserId("534329");
            client1.setFirstname("Ismail");
            client1.setLastname("damolach");
            client1.setUsername("ismail");
            client1.setEmail("ismail@gmail.com");
            client1.setJoinDate(new Date());
            client1.setPassword(bCryptPasswordEncoder.encode("123456"));
            client1.setActive(true);
            client1.setNotLocked(true);
            client1.setRole(ROLE_CLIENT.name());
            client1.setAuthorities(ROLE_CLIENT.getAuthorities());
            userRepositoryBean.save(client1);

            Client client2 = new Client();
            client2.setUserId("534329");
            client2.setFirstname("Mohammed");
            client2.setLastname("yamasoli");
            client2.setUsername("mohammed");
            client2.setEmail("mohammed@gmail.com");
            client2.setJoinDate(new Date());
            client2.setPassword(bCryptPasswordEncoder.encode("123456"));
            client2.setActive(true);
            client2.setNotLocked(true);
            client2.setRole(ROLE_CLIENT.name());
            client2.setAuthorities(ROLE_CLIENT.getAuthorities());
            userRepositoryBean.save(client2);

            Hotel hotel1 = new Hotel();
            hotel1.setName("Millenium Hotels & Resorts");
            hotel1.setDescription("A Boutique Hotel To Make Your Dreams of A Lush Island Getaway Come True. A Tropical Island Paradise On Mauritius’ Shores - La Maison D’Été.");
            hotel1.setImageUrl("assets/images/hotels/hotel-1.jpg");
            hotel1.setAvailable(true);
            hotel1.setApproved(false);
            hotel1.setOwner(owner1);
            hotelServiceBean.addHotel(hotel1);

            Hotel hotel2 = new Hotel();
            hotel2.setName("Waltzing Matilda Hotel");
            hotel2.setDescription("The Panoramic Hotel is a modern, elegant, nice and glamorous 5-star hotel overlooking the sea, perfect for a romantic, charming and a nice vacation." );
            hotel2.setImageUrl("assets/images/hotels/hotel-2.jpg");
            hotel2.setAvailable(true);
            hotel2.setApproved(false);
            hotel2.setOwner(owner1);
            hotelServiceBean.addHotel(hotel2);

            Hotel hotel3 = new Hotel();
            hotel3.setName("Sand Castle Inn & Suites");
            hotel3.setDescription("While you enjoy a cocktail by the swimming pool on the rooftop terrace, you will be stunned by the breathtaking view of the bay of Isola Bella.");
            hotel3.setImageUrl("assets/images/hotels/hotel-3.jpg");
            hotel3.setAvailable(true);
            hotel3.setApproved(false);
            hotel3.setOwner(owner2);
            hotelServiceBean.addHotel(hotel3);

            Room room1 = new Room();
            room1.setName("Bright Velo - Mystic ");
            room1.setDescription("The self check in boxes are a great idea. We arrived after 6pm, after hiking the beautiful Bright mountains all day, the owner had sent us a text early");
            room1.setPrice(249.00F);
            room1.setImageUrl("assets/images/rooms/room-1.jpg");
            room1.setAvailable(true);
            room1.setHotel(hotel1);
            roomServiceBean.addRoom(room1);

            Room room2 = new Room();
            room2.setName("Alpine Hotel Bright");
            room2.setDescription("Bright Velo - Heritage is a 4-star property located in Bright. This 4-star hotel features free WiFi and a restaurant. The nearest airport is Albury");
            room2.setPrice(449.00F);
            room2.setImageUrl("assets/images/rooms/room-2.jpg");
            room2.setAvailable(true);
            room2.setHotel(hotel1);
            roomServiceBean.addRoom(room2);

            Room room3 = new Room();
            room3.setName("Discovery Parks - Bright");
            room3.setDescription("Set in Bright, Alpine Hotel Bright features a restaurant, bar and free WiFi throughout the property. The room was great! It was very clean, great");
            room3.setPrice(129.00F);
            room3.setImageUrl("assets/images/rooms/room-3.jpg");
            room3.setAvailable(true);
            room3.setHotel(hotel2);
            roomServiceBean.addRoom(room3);

            Room room4 = new Room();
            room4.setName("Tasman Holiday Parks");
            room4.setDescription("Just a 5-minute drive from the heart of Bright and set on 12 acres on the banks of Ovens River, Discovery Parks - Bright boasts views of Mount");
            room4.setPrice(349.00F);
            room4.setImageUrl("assets/images/rooms/room-4.jpg");
            room4.setAvailable(true);
            room4.setHotel(hotel3);
            roomServiceBean.addRoom(room4);

            /*
            Reservation reservation1 = new Reservation();
            reservation1.setClient(client);
            reservation1.setRoom(room1);
            reservationServiceBean.addReservation(reservation1);

            Reservation reservation2 = new Reservation();
            reservation2.setRoom(room2);
            reservation2.setClient(client);
            reservationServiceBean.addReservation(reservation2);
             */

        };

    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

}
