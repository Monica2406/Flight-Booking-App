package com.air_app.entity;

import java.util.Date;

public class Flight {
		private String flightId;
		private String flight_src;
		private String flight_desc;
		private int availableSeats;
		private Date dateOfJourney;
		
		public Date getDateOfJourney() {
			return dateOfJourney;
		}
		public void setDateOfJourney(Date dateOfJourney) {
			this.dateOfJourney = dateOfJourney;
		}
		public Flight(String flightId, String source, String destination, int availableSeats, Date dateOfJourney) {
		    super();
		    this.flightId = flightId;
		    this.flight_src = source;
		    this.flight_desc = destination;
		    this.availableSeats = availableSeats;
		    this.dateOfJourney = dateOfJourney;
		}

		public String getFlightId() {
			return flightId;
		}
		public void setFlightId(String flightId) {
			this.flightId = flightId;
		}
		public String getFlight_src() {
			return flight_src;
		}
		public void setFlight_src(String flight_src) {
			this.flight_src = flight_src;
		}
		public String getFlight_desc() {
			return flight_desc;
		}
		public void setFlight_desc(String flight_desc) {
			this.flight_desc = flight_desc;
		}
		public int getAvailableSeats() {
			return availableSeats;
		}
		public void setAvailableSeats(int availableSeats) {
			this.availableSeats = availableSeats;
		}
		@Override
		public String toString() {
			return "Flight [flightId=" + flightId + ", flight_src=" + flight_src + ", availableSeats=" + availableSeats + ", dateOfJourney=" + dateOfJourney + "]";

		}
		

	}

	