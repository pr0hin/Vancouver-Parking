package com.ubc.cpsc310.vancouverparking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.ubc.cpsc310.vancouverparking.server.Meter;
import com.ubc.cpsc310.vancouverparking.server.TimeInEffect;
import com.ubc.cpsc310.vancouverparking.server.TimeLimit;



public class MeterTest {

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	private Meter m1 = new Meter(1);
	private Meter m2 = new Meter(2);
	private Meter m3 = new Meter(3);
	

	@Before
	public void setUp() {
		helper.setUp();
		m2.setRate(2.00);
		m2.setCreditCard(false);
		m3.setRate(3.00);
		m3.setLatitude(125.31232222);
		m3.setLongitude(423.12223311);
		//m3.setLatlng(125.31232222, 423.12223311);
		m3.setTimeInEffect(new TimeInEffect(10.0, 16.00));
		m3.setTimeLimit(new TimeLimit(3.5));
		m3.setType("Double head");
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void TestDataStore() {

		PersistenceManager pm;
		Meter r1;
		Meter r2;
		Meter r3;
		// prove MyObject doesn't span tests
		pm = PMF.getPersistenceManager();
		boolean notFound = false;
		try {
			pm.getObjectById(Meter.class, 1);
			fail("should have raised not found");
		} catch (JDOObjectNotFoundException e) {
			notFound = true;
		} finally {
			pm.close();
		}
		assertTrue(notFound);

		pm = PMF.getPersistenceManager();
		try {
			pm.makePersistent(m1);
			pm.makePersistent(m2);
			pm.makePersistent(m3);
		} finally {
			pm.close();
		}

		pm = PMF.getPersistenceManager();
		try {
			r1 = pm.getObjectById(Meter.class, 1);
			r2 = pm.getObjectById(Meter.class, 2);
			r3 = pm.getObjectById(Meter.class, 3);
		} finally {
			pm.close();
		}

		assertNotNull(pm);
		assertEquals(1, r1.getNumber());
		assertEquals(2, r2.getNumber());
		assertEquals(3, r3.getNumber());
		
		assertTrue(0 == r1.getRate());
		assertTrue(2.00 == r2.getRate());
		
		assertTrue(3 == r3.getRate());
		assertTrue(125.31232222 == r3.getLatitude());
		assertTrue(423.12223311 == r3.getLongitude());
//		TimeInEffect t3m = m3.getTimeInEffect();
//		TimeInEffect t3r = r3.getTimeInEffect();
//		assertNotNull(t3m);
//		assertNotNull(t3r);
//		assertTrue(t3m.getStart() == 10.0);
//		assertTrue(t3r.getStart() == 10.0);
//		assertTrue(r3.getTimeInEffect().getEnd() == 16.00);
//		assertTrue(r3.getTimeInEffect().equals(new TimeInEffect(10.0, 16.00)));
		assertTrue(r3.getType().equals("Double head"));
		//assertTrue(r3.getLatlng().lng() == 423.12223311);
	}
}
