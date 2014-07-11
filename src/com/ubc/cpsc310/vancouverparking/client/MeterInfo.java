package com.ubc.cpsc310.vancouverparking.client;

import java.io.Serializable;
import java.util.List;

//import com.ubc.cpsc310.vancouverparking.server.TimeInEffect;
//import com.ubc.cpsc310.vancouverparking.server.TimeLimit;

public class MeterInfo implements Serializable {

	private long number;
	private double latitude;
	private double longitude;
	private double rate;
	private boolean creditCard;
	private String type;
	private double tieStart;
	private double tieEnd;
	private double timeLimit;
	private boolean favorite;
	private String timeInEffect;


	public String getTimeInEffect() {
		return timeInEffect;
	}

	public void setTimeInEffect(String timeInEffect) {
		this.timeInEffect = timeInEffect;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(List<Long> listFavorites) {
		this.favorite = listFavorites.contains(this.number);
	}

	public void setFavorite(boolean fav) {
		this.favorite = fav;
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

	public void setNumber(long n) {
		this.number = n;
		
	}
	
}
