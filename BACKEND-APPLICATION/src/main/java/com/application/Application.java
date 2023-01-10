package com.application;

import com.application.entities.*;
import com.application.repositories.CityRepository;
import com.application.repositories.UserRepository;
import com.application.services.specifications.HotelServiceSpecification;
import com.application.services.specifications.ReservationServiceSpecification;
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

import static com.application.constants.FileConstants.USER_FOLDER;
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
            CityRepository cityRepositoryBean,
            HotelServiceSpecification hotelServiceBean,
            RoomServiceSpecification roomServiceBean,
            ReservationServiceSpecification reservationServiceBean,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ){

        return args -> {

            Admin admin = new Admin();
            admin.setUserId("534376");
            admin.setFirstname("hassan");
            admin.setLastname("touza");
            admin.setUsername("touzaelhassan");
            admin.setEmail("hassan@gmail.com");
            admin.setJoinDate(new Date());
            admin.setPassword(bCryptPasswordEncoder.encode("123456"));
            admin.setActive(true);
            admin.setNotLocked(true);
            admin.setRole(ROLE_SUPER_ADMIN.name());
            admin.setAuthorities(ROLE_SUPER_ADMIN.getAuthorities());
            admin.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(admin);

            Owner owner1 = new Owner();
            owner1.setUserId("534345");
            owner1.setFirstname("karim");
            owner1.setLastname("lam");
            owner1.setUsername("lamkarim");
            owner1.setEmail("karim@gmail.com");
            owner1.setJoinDate(new Date());
            owner1.setPassword(bCryptPasswordEncoder.encode("123456"));
            owner1.setActive(true);
            owner1.setNotLocked(true);
            owner1.setRole(ROLE_OWNER.name());
            owner1.setAuthorities(ROLE_OWNER.getAuthorities());
            owner1.setProfileImageUrl("www.url.com/image-url1");
            userRepositoryBean.save(owner1);

            Owner owner2 = new Owner();
            owner2.setUserId("534345");
            owner2.setFirstname("amine");
            owner2.setLastname("kal");
            owner2.setUsername("kalamine");
            owner2.setEmail("amine@gmail.com");
            owner2.setJoinDate(new Date());
            owner2.setPassword(bCryptPasswordEncoder.encode("123456"));
            owner2.setActive(true);
            owner2.setNotLocked(true);
            owner2.setRole(ROLE_OWNER.name());
            owner2.setAuthorities(ROLE_OWNER.getAuthorities());
            owner2.setProfileImageUrl("www.url.com/image-url2");
            userRepositoryBean.save(owner2);

            Client client1 = new Client();
            client1.setUserId("534329");
            client1.setFirstname("ismail");
            client1.setLastname("damo");
            client1.setUsername("damoismail");
            client1.setEmail("ismail@gmail.com");
            client1.setJoinDate(new Date());
            client1.setPassword(bCryptPasswordEncoder.encode("123456"));
            client1.setActive(true);
            client1.setNotLocked(true);
            client1.setRole(ROLE_CLIENT.name());
            client1.setAuthorities(ROLE_CLIENT.getAuthorities());
            client1.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(client1);

            Client client2 = new Client();
            client2.setUserId("534329");
            client2.setFirstname("adil");
            client2.setLastname("yam");
            client2.setUsername("yamadil");
            client2.setEmail("adil@gmail.com");
            client2.setJoinDate(new Date());
            client2.setPassword(bCryptPasswordEncoder.encode("123456"));
            client2.setActive(true);
            client2.setNotLocked(true);
            client2.setRole(ROLE_CLIENT.name());
            client2.setAuthorities(ROLE_CLIENT.getAuthorities());
            client2.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(client2);

            City city1 = new City();
            city1.setName("Fes");
            City city2= new City();
            city2.setName("Rabat");
            City city3 = new City();
            city3.setName("Tanger");
            cityRepositoryBean.save(city1);
            cityRepositoryBean.save(city2);
            cityRepositoryBean.save(city3);

            Hotel hotel1 = new Hotel();
            hotel1.setName("VERONEX-5");
            hotel1.setAvailable(true);
            hotel1.setApproved(false);
            hotel1.setOwner(owner1);
            hotel1.setCity(city1);
            hotelServiceBean.addHotel(hotel1);

            Hotel hotel2 = new Hotel();
            hotel2.setName("MALERT-457");
            hotel2.setAvailable(true);
            hotel2.setApproved(false);
            hotel2.setOwner(owner1);
            hotel2.setCity(city2);
            hotelServiceBean.addHotel(hotel2);

            Hotel hotel3 = new Hotel();
            hotel3.setName("B-HOUSE 55");
            hotel3.setAvailable(true);
            hotel3.setApproved(false);
            hotel3.setOwner(owner2);
            hotel3.setCity(city3);
            hotelServiceBean.addHotel(hotel3);

            Room room1 = new Room();
            room1.setName("room1");
            room1.setAvailable(true);
            room1.setHotel(hotel1);
            roomServiceBean.addRoom(room1);

            Room room2 = new Room();
            room2.setName("room2");
            room2.setAvailable(true);
            room2.setHotel(hotel1);
            roomServiceBean.addRoom(room2);

            Room room3 = new Room();
            room3.setName("room3");
            room3.setAvailable(true);
            room3.setHotel(hotel2);
            roomServiceBean.addRoom(room3);

            Room room4 = new Room();
            room4.setName("room4");
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
