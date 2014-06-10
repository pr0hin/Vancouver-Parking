package com.ubc.cpsc310.vancouverparking.server;

public class Meter {

	private int number;
	private double latitude;
	private double longitude;
	private double rate;
	private Time timeInEffect;		
	private boolean creditCard;
	private String type;
	private int timeLimit;			// making hour class? (could be int)
	
	public Meter(int number, double lat, double lon, double rate, Time inEffect, boolean creditCard, String type, int timeLimit) {
		this.number = number;
		this.latitude = lat;
		this.longitude = lon;
		this.rate = rate;
		this.timeInEffect = inEffect;
		this.creditCard = creditCard;
		this.type = type;
		this.timeLimit = timeLimit;
	}
	
}
