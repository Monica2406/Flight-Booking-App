package com.air_app.service;

import java.util.Date;

import java.util.Scanner;

public interface FlightService {
	void searchFlight(Scanner sc, String username);
	void printBoardingPass();
	void printBoardingPass(String username, String flightId, String flight_src, String flight_desc, Date dateOfJourney);
	

}
