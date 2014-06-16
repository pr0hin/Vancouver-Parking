package com.ubc.cpsc310.vancouverparking.server;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Meter {

	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
	@Persistent
	private long number;

	@Persistent
	private double latitude;

	@Persistent
	private double longitude;
	
	@Persistent
	private float rate;

	@Persistent
	private String timeInEffect;

	@Persistent
	private boolean creditCard;

	@Persistent
	private String type;
 
	@Persistent
	private Date createDate;

	@Persistent
	private float tieStart;
	
	@Persistent
	private float tieEnd;
	
	@Persistent
	private float timeLimit;
	
	public Meter() {
		this.createDate = new Date();
	}

	public Meter(int number, String type, float rate, float timeLimit, boolean creditCard, String timeInEffect, double latitude, double longitude) {
		this();
		this.number = number;
		this.type = type;
		this.rate = rate;
		this.timeLimit = timeLimit;
		this.creditCard = creditCard;
		this.timeInEffect = timeInEffect;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public Meter(long number) {
		this();
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

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public float getTieStart() {
		return tieStart;
	}

	public void setTieStart(float tieStart) {
		this.tieStart = tieStart;
	}

	public float getTieEnd() {
		return tieEnd;
	}

	public void setTieEnd(float tieEnd) {
		this.tieEnd = tieEnd;
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

	public void setNumber(long number) {
		this.number = number;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (creditCard ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (number ^ (number >>> 32));
		temp = Double.doubleToLongBits(rate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tieEnd);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(tieStart);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(timeLimit);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meter other = (Meter) obj;
		if (creditCard != other.creditCard)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (number != other.number)
			return false;
		if (Double.doubleToLongBits(rate) != Double
				.doubleToLongBits(other.rate))
			return false;
		if (Double.doubleToLongBits(tieEnd) != Double
				.doubleToLongBits(other.tieEnd))
			return false;
		if (Double.doubleToLongBits(tieStart) != Double
				.doubleToLongBits(other.tieStart))
			return false;
		if (Double.doubleToLongBits(timeLimit) != Double
				.doubleToLongBits(other.timeLimit))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


}
