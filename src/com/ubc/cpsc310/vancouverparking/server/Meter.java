package com.ubc.cpsc310.vancouverparking.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Meter {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private int number;
	private double latitude;
	private double longitude;
	private double rate;
	private Time timeInEffect;		
	private boolean creditCard;
	private String type;
	private TimeLimit timeLimit;			// making hour class? (could be int)
	
	public Meter(int number) {
		this.number = number;
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

	public Time getTimeInEffect() {
		return timeInEffect;
	}

	public void setTimeInEffect(Time timeInEffect) {
		this.timeInEffect = timeInEffect;
	}

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

	public TimeLimit getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(TimeLimit timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	
}
