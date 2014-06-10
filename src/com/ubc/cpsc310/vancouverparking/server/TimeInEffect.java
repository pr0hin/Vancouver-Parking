package com.ubc.cpsc310.vancouverparking.server;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class TimeInEffect {
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	@Persistent
	private double start;
	@Persistent
	private double end;
	
	public TimeInEffect(double s, double e){
		this.start = s;
		this.end = e;
	}
	
}
