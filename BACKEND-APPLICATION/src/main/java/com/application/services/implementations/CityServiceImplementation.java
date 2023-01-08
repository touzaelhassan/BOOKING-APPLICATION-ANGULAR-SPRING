package com.application.services.implementations;

import com.application.entities.City;
import com.application.repositories.CityRepository;
import com.application.services.specifications.CityServiceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cityServiceBean")
public class CityServiceImplementation implements CityServiceSpecification {

    private final CityRepository cityRepositoryBean;

    @Autowired
    public CityServiceImplementation(CityRepository cityRepositoryBean) { this.cityRepositoryBean = cityRepositoryBean; }

    @Override
    public City addCity(City city) {return cityRepositoryBean.save(city); }

}
