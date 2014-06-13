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
	@Persistent//(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private long number;
	
	@Persistent
	private double latitude;
	
	@Persistent
	private double longitude;
	
	@Persistent
	private double rate;

	@Persistent
	private boolean creditCard;
	
	@Persistent
	private String type;
 
	@Persistent
	private Date createDate;

	@Persistent
	private double tieStart;
	
	@Persistent
	private double tieEnd;
	
	@Persistent
	private double timeLimit;
	
	public Meter() {
		this.createDate = new Date();
	}


	public Meter(long number) {
		this();
		this.number = number;
	}

	public long getNumber() {
		return number;
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


	public double getTieStart() {
		return tieStart;
	}


	public void setTieStart(double tieStart) {
		this.tieStart = tieStart;
	}


	public double getTieEnd() {
		return tieEnd;
	}


	public void setTieEnd(double tieEnd) {
		this.tieEnd = tieEnd;
	}


	public double getTimeLimit() {
		return timeLimit;
	}


	public void setTimeLimit(double timeLimit) {
		this.timeLimit = timeLimit;
	}

	
}
