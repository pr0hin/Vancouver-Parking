package com.ubc.cpsc310.vancouverparking.client;

import java.io.Serializable;

//import com.ubc.cpsc310.vancouverparking.server.TimeInEffect;
//import com.ubc.cpsc310.vancouverparking.server.TimeLimit;

public class MeterInfo implements Serializable {

	/**
	 * 
	 */
	private long number;
	private double latitude;
	private double longitude;
	private double rate;
	// private TimeInEffect timeInEffect;
	private boolean creditCard;
	private String type;
	//private TimeLimit timeLimit; // making hour class? (could be int)


	public long getNumber() {
		return number;
	}
	
	public void setNumber(long n){
		this.number = n;
	}
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

//	public TimeInEffect getTimeInEffect() {
//		return timeInEffect;
//	}
//
//	public void setTimeInEffect(TimeInEffect timeInEffect) {
//		this.timeInEffect = timeInEffect;
//	}

	public boolean isCreditCard() {
		return creditCard;
	}

	public void setCreditCard(boolean creditCard) {
		this.creditCard = creditCard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
//
//	public TimeLimit getTimeLimit() {
//		return timeLimit;
//	}
//
//	public void setTimeLimit(TimeLimit timeLimit) {
//		this.timeLimit = timeLimit;
//	}

	
}
