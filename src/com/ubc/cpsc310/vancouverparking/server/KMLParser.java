package com.ubc.cpsc310.vancouverparking.server;

import java.util.List;


public class KMLParser {


	public List<Meter> parseFile() {
		MeterDataStub metersStub = new MeterDataStub();
		return metersStub.getMetersList();
	}
	
}
