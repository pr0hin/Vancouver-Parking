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
		System.out.println(kmlparser.getPath());
	}
	
	
	@Test
	public void checkSizeOfPlacemarks() {

		assertEquals(9860, kmlparser.getPlacemarks().size());
		assertEquals(9860, kmlparser.getMeters().size());
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

}
