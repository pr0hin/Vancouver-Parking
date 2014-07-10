package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class SearchHistory {
	@PrimaryKey
	private String email;
	@Persistent
	private List<String> history;
	@Persistent
	private Date createDate;

	private SearchHistory() {
		this.createDate = new Date();
		this.history = new ArrayList<String>();
	}

	public SearchHistory(String email) {
		this();
		this.email = email;
	}

	public String getId() {
		return this.email;
	}

	public List<String> getHistory() {
		return this.history;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void addHistory(String location) {
		this.history.add(location);
	}
	
}
