package com.cabManagement.Service;

import com.cabManagement.Pojos.City;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
@Singleton
public class CityService {
    private Map<String, City> cities = new ConcurrentHashMap<>();
    private List<String> cityIds = new ArrayList<>();

    public synchronized Boolean addCity(City city) {
        if(!cities.containsKey(city.getCityId())) {
            cities.put(city.getCityId(), city);
            cityIds.add(city.getCityId());
            return true;
        }
        return false;
    }

    public City getCity(String cityId) {
        if(cities.containsKey(cityId))
            return cities.get(cityId);
        return null;
    }
}
