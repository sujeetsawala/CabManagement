package com.cabManagement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    static final String ServerHostName = "localhost";
    static final int ServerPort = 8080;

    public static void main(String args[]) {
        try {
            Socket socket = new Socket(ServerHostName, ServerPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please select one of the following options");
            try {

                while (true) {
                    System.out.println("1. Book a cab");
                    System.out.println("2. Complete ride");
                    System.out.println("3. Register a cab");
                    System.out.println("4. Onboard a city");
                    System.out.println("5. Change cab current city");
                    System.out.println("6. Get Cab idle Time");
                    System.out.println("7. Get Cab Info");


                    /**
                     *  Book Cab: 1 CityId
                     *  Complete Ride: 2 CabId
                     *  Register a cab: 3 cabId cityId
                     *  Onboard city: 4 cityId cityName
                     *  Change cab current City: 5 cabId cityId
                     */

                    String request = keyboard.readLine();
                    out.println(request);
                    System.out.println(in.readLine());
                }
            } finally {
                in.close();
                out.close();
                keyboard.close();
                socket.close();
            }
        } catch (Exception e) {
            System.err.println("Error Connecting socket :" + e.getMessage());
        }
    }
}
