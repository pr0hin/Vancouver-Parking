package com.ubc.cpsc310.vancouverparking.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Meter {

	@PrimaryKey
	@Persistent
	// (valueStrategy = IdGeneratorStrategy.IDENTITY)
	private long number;

	@Persistent
	private double latitude;

	@Persistent
	private double longitude;

	// @Persistent
	// private double latlng;

	@Persistent
	private double rate;

	@Persistent
	private String timeInEffect;

	@Persistent
	private boolean creditCard;

	@Persistent
	private String type;

	@Persistent
	private Date createDate;

	public Meter() {
		this.createDate = new Date();
	}

	@Persistent
	private float timeLimit; // making hour class? (could be int)

	public Meter(int number, String type, double rate, float timeLimit, boolean creditCard, String timeInEffect) {
		this.number = number;
		this.type = type;
		this.rate = rate;
		this.timeLimit = timeLimit;
		this.creditCard = creditCard;
		this.timeInEffect = timeInEffect;
	}

	public long getNumber() {
		return number;
	}

	public double getLatitude() {
		return latitude;
	}

	// public void setLatlng(double lat, double lng){
	// this.latlng = LatLng.create(lat, lng);
	// }
	//
	// public LatLng getLatlng(){
	// return this.latlng;
	// }

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

	// public TimeInEffect getTimeInEffect() {
	// return timeInEffect;
	// }
	//
	// public void setTimeInEffect(TimeInEffect timeInEffect) {
	// this.timeInEffect = timeInEffect;
	// }

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

	public String getTimeInEffect() {
		return timeInEffect;
	}

	public void setTimeInEffect(String timeInEffect) {
		this.timeInEffect = timeInEffect;
	}

	public float getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(float timeLimit) {
		this.timeLimit = timeLimit;
	}

	// public TimeLimit getTimeLimit() {
	// return timeLimit;
	// }
	//
	// public void setTimeLimit(TimeLimit timeLimit) {
	// this.timeLimit = timeLimit;
	// }

	
}
