package com.air_app;

import java.util.Scanner;
import com.air_app.Configuration.DBConfiguration;
import com.air_app.payLoad.LoginResponse;
import com.air_app.service.LoginService;
import com.air_app.service.LoginServiceImpl;
import com.air_app.service.RegistrationService;
import com.air_app.service.RegistrationServiceImpl;
import com.air_app.service.FlightService;
import com.air_app.service.FlightServiceImpl;

public class AirApp {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        RegistrationService rs = new RegistrationServiceImpl();
        LoginService ls = new LoginServiceImpl();
        FlightService flightService = new FlightServiceImpl();

        System.out.println("Welcome to AirApp");
        Thread.sleep(2000);
        System.out.println("Starting the app server. Please wait....");
        Thread.sleep(5000);

        char res;

        do {
            System.out.println("Select option:");
            System.out.println("1. New User    2. Already a member");
            int option = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (option) {
                case 1:
                    String registrationResponse = rs.registration(sc);
                    System.out.println(registrationResponse);
                    break;
                case 2:
                    LoginResponse loginResponse = ls.login(sc);
                    if (loginResponse != null && loginResponse.isLoggedIn()) { // Check for null
                        System.out.println("Login successful");
                        flightService.searchFlight(sc, loginResponse.getUsername()); // Corrected method
                    } else {
                        System.out.println("Login failed");
                    }
                    break;
                default:
                    System.out.println("Invalid Selection");
            }
            System.out.println("Do you want to continue? (y/n)");
            res = sc.next().toLowerCase().charAt(0);
        } while (res == 'y');

        System.out.println("Thanks for using AirApp");
        sc.close();
    }
}
