package com.cabManagement.Controller;

import com.cabManagement.Pojos.Cab;
import com.cabManagement.Pojos.CabState;
import com.cabManagement.Pojos.City;
import com.cabManagement.Pojos.TravelInfo;
import com.cabManagement.Service.AnalyticsService;
import com.cabManagement.Service.CabService;
import com.cabManagement.Service.CityService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.Date;
import java.util.List;

@Singleton
public class CabManagementController {

    private CabService cabService;
    private CityService cityService;
    private AnalyticsService analyticsService;

    @Inject
    public CabManagementController(CabService cabService,
                                   CityService cityService,
                                   AnalyticsService analyticsService) {
        this.cabService = cabService;
        this.cityService = cityService;
        this.analyticsService = analyticsService;
    }

    public void init() {
        City city1 = new City("1", "Pune");
        City city2 = new City("2", "Bangalore");


        Cab cab1 = new Cab("1", "1");
        Cab cab2 = new Cab("2", "1");
        Cab cab3 = new Cab("3", "1");

        Cab cab4 = new Cab("4", "2");
        Cab cab5 = new Cab("5", "2");
        Cab cab6 = new Cab("6", "2");

        this.cityService.addCity(city1);
        this.cityService.addCity(city2);


        this.cabService.addCab(cab1);
        this.cabService.addCab(cab2);
        this.cabService.addCab(cab3);
        this.cabService.addCab(cab4);
        this.cabService.addCab(cab5);
        this.cabService.addCab(cab6);

    }

    public Boolean registerCab(String cabId, String cityId) {
        Cab cab = new Cab(cabId, cityId);
        return this.cabService.addCab(cab);
    }

    public Boolean changeCabCurrentCity(String cabId, String cityId) {
        return this.cabService.updateCabLocation(cabId, cityId);
    }

    public Boolean onboardCity(String cityId, String cityName ) {
        City city = new City(cityId, cityName);
        return this.cityService.addCity(city);
    }

    public String bookCab(String currentCityId, String destinationCityId) {
       String reqCabId =  this.cabService.bookCab(currentCityId, destinationCityId);
       return reqCabId;
    }

    public void completeRide(String cabId) {
        this.cabService.completeCabRide(cabId, new Date().getTime());
    }

    public List<TravelInfo> getCabTravelInfo(String cabId) {
       return this.analyticsService.getCabTravelInfo(cabId);
    }

    public Long getCabIdleTime(String cabId, Long startTimestamp, Long endTimestamp) {
        return this.analyticsService.getCabIdleTime(cabId, startTimestamp, endTimestamp);
    }
}
