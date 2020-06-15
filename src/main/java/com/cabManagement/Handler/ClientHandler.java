package com.cabManagement.Handler;

import com.cabManagement.Controller.CabManagementController;
import com.cabManagement.Pojos.TravelInfo;

import java.io.*;
import java.net.Socket;
import java.util.List;

import static java.lang.Long.parseLong;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private CabManagementController cabManagementController;

    public ClientHandler(Socket socket, CabManagementController cabManagementController) throws IOException {
        this.socket = socket;
        this.cabManagementController = cabManagementController;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    }
    @Override
    public void run(){
        try {
            while(true) {
                String[] request = in.readLine().split("\\s+");
                switch (request[0]) {
                    case "1": {
                        String result = cabManagementController.bookCab(request[1], request[2]);
                        out.println("Cab booked with cabId: " + result);
                        break;
                    }
                    case "2": {
                        cabManagementController.completeRide(request[1]);
                        out.println("Completed Ride");
                        break;
                    }
                    case "3": {
                        cabManagementController.registerCab(request[1], request[2]);
                        out.println("Cab registered");
                        break;
                    }
                    case "4": {
                        Boolean result = cabManagementController.onboardCities(request[1], request[2]);
                        out.println("City Onboarded: " + result);
                        break;
                    }
                    case "5": {
                        Boolean result = cabManagementController.changeCabCurrentCity(request[1], request[2]);
                        out.println("Current City changed: " + result);
                        break;
                    }
                    case "6": {
                        Long idleTime = cabManagementController.getCabIdleTime(request[1], parseLong(request[2]), parseLong(request[3]));
                        out.println("Idle Time: " + idleTime);
                        break;
                    }
                    case "7": {
                        List<TravelInfo> cabTravelInfos = cabManagementController.getCabTravelInfo(request[1]);
                        for(TravelInfo travelInfo: cabTravelInfos) {
                            System.out.println("CabId: " + travelInfo.getCabId());
                            System.out.println("StartCity: " + travelInfo.getStartCity());
                            System.out.println("DestinationCity:" + travelInfo.getDestinationCity());
                            System.out.println("StartTimestamp:" + travelInfo.getStartTimestamp());
                            System.out.println("EndTimeStamp: " + travelInfo.getEndTimestamp());
                            System.out.println();
                        }
                        out.println("Cab Travel Info successful");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in Connection " + e.getMessage());
        }
    }
}
