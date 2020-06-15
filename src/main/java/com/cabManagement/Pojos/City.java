package com.cabManagement.Pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {
    private String cityId;
    private String cityName;

    public City(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }
}
