package com.cabManagement;

import com.cabManagement.Controller.CabManagementController;
import com.cabManagement.Handler.ClientHandler;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.net.ServerSocket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CabManagementServer {
    static final int PORT = 8080;
    static final boolean verbose = true;
    static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {
        try {
            Injector injector = Guice.createInjector();
            CabManagementController cabManagementController = injector.getInstance(CabManagementController.class);
            // cabManagementController.init();

            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");

            while (true) {
                 ClientHandler clientHandler =new ClientHandler(serverSocket.accept(), cabManagementController);

                if (verbose) {
                    System.out.println("Connection opened. (" + new Date() + ")");
                }

                // create dedicated thread to manage the client connection
                threadPool.execute(clientHandler);
            }
        } catch (Exception e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }
}
