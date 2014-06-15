package com.ubc.cpsc310.vancouverparking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ubc.cpsc310.vancouverparking.server.KMLParser;

public class KMLParserTest {
	public KMLParser kmlparser;
	
	@Before
	public void checkIfFilePresent() {
		kmlparser = new KMLParser();
		kmlparser.parse();
	}
	
	
	@Test
	public void checkSizeOfPlacemarks() {

		assertEquals(9860, kmlparser.getPlacemarks().size());
		assertEquals(9860, kmlparser.getMeters().size());
	}
	
	@Test
	public void checkFailingMeters() {
		kmlparser.getMetersRateParsing();
	}

}
