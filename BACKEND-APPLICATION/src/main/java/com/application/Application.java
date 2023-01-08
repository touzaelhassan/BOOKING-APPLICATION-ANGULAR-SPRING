package com.application;

import com.application.entities.User;
import com.application.repositories.UserRepository;
import com.application.services.specifications.UserServiceSpecification;
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
import static com.application.enums.Role.ROLE_SUPER_ADMIN;
import static com.application.enums.Role.ROLE_USER;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        new File(USER_FOLDER).mkdirs();
    }

    @Bean
    CommandLineRunner run(
            UserRepository userRepositoryBean,
            BCryptPasswordEncoder bCryptPasswordEncoder
    ){

        return args -> {

           /*
           User user = new User();
            user.setUserId("534376");
            user.setFirstname("hassan");
            user.setLastname("touza");
            user.setUsername("touzaelhassan");
            user.setEmail("hassan@gmail.com");
            user.setJoinDate(new Date());
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setActive(true);
            user.setNotLocked(true);
            user.setRole(ROLE_SUPER_ADMIN.name());
            user.setAuthorities(ROLE_SUPER_ADMIN.getAuthorities());
            user.setProfileImageUrl("www.url.com/image-url");
            userRepositoryBean.save(user);
            System.out.println(user);
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
