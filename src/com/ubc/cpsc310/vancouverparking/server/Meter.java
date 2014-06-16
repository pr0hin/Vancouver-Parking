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
