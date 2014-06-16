package com.ubc.cpsc310.vancouverparking.server;

import java.util.LinkedList;
import java.util.List;

// Creates a list of 200 dummy meters for use in testing

public class MeterDataStub {

	private List<Meter> metersList;

	public MeterDataStub() {
		metersList = new LinkedList<Meter>();
		boolean cc = true;
		for (int i = 0; i < 200; i++) {
			Meter meter = new Meter(378625 + i);
			if (i % 20 == 0)
				cc = false;
			meter.setCreditCard(cc);
			if (i % 2 == 0) {
				meter.setRate(2.00f);
			} else {
				meter.setRate(3.00f);
			}
			meter.setType("Public");
			double latitude = 49.2569777 - (Math.random() * 0.1);
			double longitude = -123.123904 - (Math.random() * 0.1);
			meter.setLatitude(latitude);
			meter.setLongitude(longitude);
			meter.setTieEnd(6.0f);
			meter.setTieStart(8.0f);
			meter.setTimeLimit(2);
			metersList.add(meter);
			
		}

	}

	public List<Meter> getMetersList() {
		return metersList;
	}

}
