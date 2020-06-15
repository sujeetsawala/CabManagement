package com.cabManagement.Pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cab {
    private String cabId;
    private CabState cabState;
    private String currentCityId;
    private String destinationCityId;
    private Long lastTripEndTimestamp;

    public Cab(String cabId, String cityId) {
        this.cabId = cabId;
        this.cabState = CabState.IDLE;
        this.currentCityId = cityId;
        this.destinationCityId = null;
        this.lastTripEndTimestamp = 0l;
    }
}
