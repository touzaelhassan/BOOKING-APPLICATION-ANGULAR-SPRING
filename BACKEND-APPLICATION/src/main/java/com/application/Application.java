package com.application;

import com.application.entities.*;
import com.application.repositories.CityRepository;
import com.application.repositories.UserRepository;
import com.application.services.specifications.HotelServiceSpecification;
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

            Owner owner = new Owner();
            owner.setUserId("534345");
            owner.setFirstname("karim");
            owner.setLastname("lampa");
            owner.setUsername("lampakarim");
            owner.setEmail("karim@gmail.com");
            owner.setJoinDate(new Date());
            owner.setPassword(bCryptPasswordEncoder.encode("123456"));
            owner.setActive(true);
            owner.setNotLocked(true);
            owner.setRole(ROLE_OWNER.name());
            owner.setAuthorities(ROLE_OWNER.getAuthorities());
            owner.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(owner);

            Client client = new Client();
            client.setUserId("534329");
            client.setFirstname("ismail");
            client.setLastname("damo");
            client.setUsername("damoismail");
            client.setEmail("ismail@gmail.com");
            client.setJoinDate(new Date());
            client.setPassword(bCryptPasswordEncoder.encode("123456"));
            client.setActive(true);
            client.setNotLocked(true);
            client.setRole(ROLE_CLIENT.name());
            client.setAuthorities(ROLE_CLIENT.getAuthorities());
            client.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(client);

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
            hotel1.setOwner(owner);
            hotel1.setCity(city1);
            hotelServiceBean.addHotel(hotel1);

            Hotel hotel2 = new Hotel();
            hotel2.setName("MALERT-457");
            hotel2.setAvailable(true);
            hotel2.setApproved(false);
            hotel2.setOwner(owner);
            hotel2.setCity(city2);
            hotelServiceBean.addHotel(hotel2);

            Hotel hotel3 = new Hotel();
            hotel3.setName("B-HOUSE 55");
            hotel3.setAvailable(true);
            hotel3.setApproved(false);
            hotel3.setOwner(owner);
            hotel3.setCity(city3);
            hotelServiceBean.addHotel(hotel3);

            Room room1 = new Room();
            room1.setName("room1");
            room1.setAvailable(true);
            room1.setHotel(hotel1);




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
