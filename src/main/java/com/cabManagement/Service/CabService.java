package com.cabManagement.Service;

import com.cabManagement.Pojos.Cab;
import com.cabManagement.Pojos.CabState;
import com.cabManagement.Pojos.TravelInfo;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Getter
@Setter
public class CabService {
    private Map<String, Cab> cabs = new ConcurrentHashMap<>();
    private List<String> cabIds = new ArrayList<>();

    private CityService cityService;
    private AnalyticsService analyticsService;

    @Inject
    public CabService(CityService cityService, AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
        this.cityService = cityService;
    }

    public synchronized void addCab(Cab cab) {
       if(!cabs.containsKey(cab.getCabId()) && this.cityService.getCity(cab.getCurrentCityId()) != null) {
           cabs.put(cab.getCabId(), cab);
           cabIds.add(cab.getCabId());
       }
    }

    public Cab getCab(String cabId) {
        if(cabs.containsKey(cabId))
            return cabs.get(cabId);
        return null;
    }

    public Boolean updateCabLocation(String cabId, String cityId) {
        Cab cab = cabs.get(cabId);
        if(cityService.getCity(cityId) != null && !cab.getCurrentCityId().equalsIgnoreCase("Indeterminate")) {
            cab.setCurrentCityId(cityId);
            cabs.put(cabId, cab);
            return true;
        }
        return false;
    }

    public String bookCab(String currentCityId, String destinationCityId) {

        if(this.cityService.getCity(currentCityId) == null || this.cityService.getCity(destinationCityId) == null)
            return null;

        String reqCabId = "0";
        Long timeStamp = new Date().getTime();
        for(String cabId: cabIds) {
            Cab cab =  cabs.get(cabId);
            if((cab.getCabState() == CabState.IDLE) && cab.getCurrentCityId().equalsIgnoreCase(currentCityId) && (cab.getLastTripEndTimestamp() < timeStamp)) {
                timeStamp = cab.getLastTripEndTimestamp();
                reqCabId = cabId;
            }
        }
        if(reqCabId != "0") {
            Cab cab = cabs.get(reqCabId);

            TravelInfo travelInfo = new TravelInfo();
            travelInfo.setCabId(cab.getCabId());
            travelInfo.setDestinationCity(destinationCityId);
            travelInfo.setStartTimestamp(new Date().getTime());
            travelInfo.setStartCity(cab.getCurrentCityId());
            this.analyticsService.add(cab.getCabId(), travelInfo);

            cab.setCurrentCityId("Indeterminate");
            cab.setDestinationCityId(destinationCityId);
            cab.setCabState(CabState.ON_TRIP);
            cabs.put(reqCabId, cab);
            return reqCabId;
        }
        return null;
    }

    public void completeCabRide(String cabId, Long timeStamp) {
        Cab cab = cabs.get(cabId);
        cab.setCabState(CabState.IDLE);
        cab.setCurrentCityId(cab.getDestinationCityId());
        cab.setDestinationCityId(null);
        cab.setLastTripEndTimestamp(timeStamp);
        cabs.put(cabId, cab);
        this.analyticsService.addEndtimestamp(cabId, new Date().getTime());
//        System.out.println(cab.getCabId());
//        System.out.println(cab.getCabState());
//        System.out.println(cab.getCurrentCityId());
//        System.out.println(cab.getDestinationCityId());
//        System.out.println(cab.getLastTripEndTimestamp());
    }
}
