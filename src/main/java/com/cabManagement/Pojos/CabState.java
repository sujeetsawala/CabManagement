package com.cabManagement.Pojos;

public enum CabState {
    IDLE("IDLE"),
    ON_TRIP("ON_TRIP");

    String stringValue;
    CabState(String cabState) {
        this.stringValue = cabState;
    }

    public String toString() {
        return stringValue;
    }

}
