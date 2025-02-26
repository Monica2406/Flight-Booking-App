package com.air_app.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.air_app.Configuration.DBConfiguration;
import com.air_app.entity.Flight;
import com.air_app.service.FlightService;

public class FlightServiceImpl implements FlightService {
    Connection con = DBConfiguration.getConnection();
    Flight flight;
    List<Flight> list = new ArrayList<>();

    @Override
    public void searchFlight(Scanner sc, String username) {
        System.out.println("Welcome " + username);
        try {
            if (con == null) {
                System.out.println("Database connection failed!");
                return;
            }

            System.out.println("Enter the source:");
            String src = sc.next();
            sc.nextLine();  // Clear buffer
            System.out.println("Enter the destination:");
            String dest = sc.next();
            sc.nextLine();  // Clear buffer
            System.out.println("Enter the date of journey (YYYY-MM-DD):");
            String journeyDateStr = sc.next();

            // Convert String to SQL Date
            Date journeyDate = Date.valueOf(journeyDateStr);

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM flights WHERE source = ? AND destination = ? AND dateOfFlight = ?"
            );
            ps.setString(1, src);
            ps.setString(2, dest);
            ps.setDate(3, journeyDate); // Use setDate for proper date handling

            ResultSet rs = ps.executeQuery();

            // Clear previous results
            list.clear();

            while (rs.next()) {
                String flightId = rs.getString(1);
                String source = rs.getString(2);
                String destination = rs.getString(3);
                int seats = rs.getInt(4);
                Date date = rs.getDate(5);
                flight = new Flight(flightId, source, destination, seats, date);
                list.add(flight);
            }

            if (!list.isEmpty()) {
                System.out.println("Available flights:");
                System.out.println("_________________________________");
                System.out.println(list);
                System.out.println("Enter the flightId to proceed..");
                String flightId = sc.next();
                
                for (Flight flight : list) {
                    if (flightId.equals(flight.getFlightId())) {
                        // Fixed method calls to match expected method names in Flight class
                        bookingTicket(sc, username, flightId, flight.getFlight_src(), flight.getFlight_desc(), flight.getAvailableSeats(), flight.getDateOfJourney());
                        return;
                    }
                }
            } else {
                System.out.println("No flights found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bookingTicket(Scanner sc, String username, String flightId, String flight_src, String flight_desc,
                               int availableSeats, java.util.Date dateOfJourney) {
        try {
            System.out.println("Enter the number of seats you want to book:");
            int requestedSeats = sc.nextInt();

            if (requestedSeats <= availableSeats) {
                availableSeats -= requestedSeats;
                PreparedStatement ps = con.prepareStatement("UPDATE flights SET availableSeats = ? WHERE flightId = ?");
                ps.setInt(1, availableSeats);
                ps.setString(2, flightId);
                int row = ps.executeUpdate();

                if (row > 0) {
                    System.out.println("Ticket booked successfully");
                    System.out.println("Initiating to print boarding pass. Please wait.....");
                    
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted while waiting.");
                    }

                    printBoardingPass(username, flightId, flight_src, flight_desc, dateOfJourney);
                }
            } else {
                System.out.println("Not enough seats available.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	@Override
	public void printBoardingPass(String username, String flightId, String flight_src, String flight_desc, java.util.Date dateOfJourney) {
		System.out.println("Your boarding pass:");
        System.out.println("Passenger Name  : " + username);
        System.out.println("FlightId        : " + flightId);
        System.out.println("Source          : " + flight_src);
        System.out.println("Destination     : " + flight_desc);
        System.out.println("Date of Journey : " + dateOfJourney);
        System.out.println("Happy Journey!!!");
        System.out.println("Have a safe Flight!!");
	}

	@Override
	public void printBoardingPass() {
		System.out.println("No Boarding Pass details available");
		// TODO Auto-generated method stub
		
	}
}
