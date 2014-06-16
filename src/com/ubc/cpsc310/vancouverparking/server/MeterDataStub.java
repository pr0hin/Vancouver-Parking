package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.List;

import com.ubc.cpsc310.vancouverparking.client.MeterInfo;


// Creates a list of 200 dummy meters for use in testing

public class MeterDataStub {

	private List<Meter> metersList;
	
	public MeterDataStub() {
		metersList = new ArrayList<Meter>();
		for (int i = 0; i<200;i++) {
			Meter meter = new Meter(378625 + i);
			meter.setCreditCard(true);
			if (i%2 == 0) {
				meter.setRate(2.00);
			}
			meter.setType("Public");
			double latitude = 49.2569777 - (Math.random() * 0.1);
			double longitude = -123.123904 - (Math.random() * 0.1);
			meter.setLatitude(latitude);
			meter.setLongitude(longitude);
			metersList.add(meter);
		}
	}
	
	public List<Meter> getMetersList() {
		return metersList;
	}
		
}
