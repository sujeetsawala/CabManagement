package com.cabManagement.Pojos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TravelInfo implements Serializable {
    private String cabId;
    private String startCity;
    private String destinationCity;
    private Long startTimestamp;
    private Long endTimestamp;
}
