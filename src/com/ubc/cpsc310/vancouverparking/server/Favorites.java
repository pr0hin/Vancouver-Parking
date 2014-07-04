package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Favorites {

	@PrimaryKey
	private String email;
	@Persistent
	private List<Long> favoriteMeters;
	@Persistent
	private Date createDate;

	private Favorites() {
		this.createDate = new Date();
		this.favoriteMeters = new ArrayList<Long>();
	}

	public Favorites(String email) {
		this();
		this.email = email;
	}

	public String getId() {
		return this.email;
	}

	public List<Long> getFavoriteMeters() {
		return this.favoriteMeters;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void addMeter(long meterNumber) {
		this.favoriteMeters.add(meterNumber);
	}
	
	public void removeMeter(long meterNumber) {
		this.favoriteMeters.remove(meterNumber);
	}

}
