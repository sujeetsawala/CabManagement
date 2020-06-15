package com.cabManagement.Service;

import com.cabManagement.Pojos.TravelInfo;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
public class AnalyticsService {
    private Map<String, List<TravelInfo>> travelInfoMap = new ConcurrentHashMap<>();

    public synchronized void add(String cabId, TravelInfo travelInfo) {
        if(travelInfoMap.containsKey(cabId)) {
            List<TravelInfo> cabTravelInfo = travelInfoMap.get(cabId);
            cabTravelInfo.add(travelInfo);
            travelInfoMap.put(cabId, cabTravelInfo);
        } else {
            List<TravelInfo> travelInfos = new ArrayList<>();
            travelInfos.add(travelInfo);
            travelInfoMap.put(cabId, travelInfos);
        }
    }

    public synchronized void addEndtimestamp(String cabId, Long endTimestamp) {
        if(travelInfoMap.containsKey(cabId)) {
            travelInfoMap.get(cabId).get(travelInfoMap.get(cabId).size() - 1).setEndTimestamp(endTimestamp);
        }
    }

    public List<TravelInfo> getCabTravelInfo(String cabId) {
        if(travelInfoMap.containsKey(cabId)) {
            return  travelInfoMap.get(cabId);
        }
        return new ArrayList<>();
    }

    public Long getCabIdleTime(String cabId, Long startTimestamp, Long endTimestamp) {
        if(travelInfoMap.containsKey(cabId)) {
            List<TravelInfo> cabTravelInfos = travelInfoMap.get(cabId);
            Long idleTimeAmount = endTimestamp - startTimestamp + 1;
            for(TravelInfo travelInfo : cabTravelInfos) {
                if(travelInfo.getEndTimestamp() < startTimestamp || travelInfo.getStartTimestamp() > endTimestamp)
                    continue;
                Long difference = Math.min(travelInfo.getEndTimestamp() != null ? travelInfo.getEndTimestamp() : endTimestamp, endTimestamp) - Math.max(travelInfo.getStartTimestamp(), startTimestamp) + 1;
                idleTimeAmount -= difference;
            }
            return idleTimeAmount;
        }
        return null;
    }
}
