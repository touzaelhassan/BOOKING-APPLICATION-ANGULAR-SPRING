package com.application.services.specifications;

import com.application.entities.City;

public interface CityServiceSpecification {
    City  addCity(City city);
    City getCityByName(String name);
}
