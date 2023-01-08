package com.application.repositories;

import com.application.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("cityRepositoryBean")
public interface CityRepository extends JpaRepository<City, Integer> { }
