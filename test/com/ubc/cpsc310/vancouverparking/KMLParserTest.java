package com.ubc.cpsc310.vancouverparking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ubc.cpsc310.vancouverparking.server.KMLParser;
import com.ubc.cpsc310.vancouverparking.server.Meter;

public class KMLParserTest {
	public KMLParser kmlparser;
	
	@Before
	public void checkIfFilePresent() {
		kmlparser = new KMLParser();
		kmlparser.parse();
	}
	
	
	@Test
	public void checkSizeOfPlacemarks() {

		assertEquals(9862, kmlparser.getPlacemarks().size());
		assertEquals(9862, kmlparser.getMeters().size());
	}
	
	@Test
	public void findUnknownHeadTypeMeters() {
		System.out.println("Meters With No Head Type");
		kmlparser.getMetersFailingTypeParsing();
		System.out.println("==============");
	}
	
	@Test
	public void findUnknownLocationMeters() {
		System.out.println("Meters with No Location");
		kmlparser.getMetersWithoutCoord();
		System.out.println("==============");
	}
	
	@Test
	public void findMetersWithoutRate() {
		System.out.println("Meters with No Rate");
		kmlparser.getMetersFailingRateParsing();
		System.out.println("==============");
	}
	
	@Test
	public void findMetersWithoutTimeLimit() {
		System.out.println("Meters without Time Limit");
		kmlparser.getMetersFailingTimeLimit();
		System.out.println("==============");	
	}
	@Test 
	public void findMetersWithoutCreditCard() {
		System.out.println("Meters without Credit Card");
		kmlparser.getMetersFailingCreditCard();
		System.out.println("==============");
	}
	
	@Test
	public void findMetersWithoutTimeInEffect() {
		System.out.println("Meters Without Time In Effect");
		kmlparser.getMetersFailingTimeInEffect();
		System.out.println("==============");
	}
	
	@Test
	public void test1meter() {
		Meter m1 = new Meter();
		for (Meter meter: kmlparser.getMeters()) {
			if (meter.getNumber() == 60018) {
				 m1 = meter;
			}
			
		}
		System.out.println(m1.getLatitude());
		System.out.println(m1.getLongitude());
	}

}
