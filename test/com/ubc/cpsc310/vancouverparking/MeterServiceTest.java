package com.ubc.cpsc310.vancouverparking;

import static org.junit.Assert.*;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ubc.cpsc310.vancouverparking.client.MeterService;
import com.ubc.cpsc310.vancouverparking.client.MeterServiceAsync;
import com.ubc.cpsc310.vancouverparking.server.Meter;


public class MeterServiceTest extends GWTTestCase{

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	private final MeterServiceAsync meterService = GWT
			.create(MeterService.class);
	
	
	@Before
	public void initialize() {
		helper.setUp();
	}

	@After
	public void close() {
		helper.tearDown();
	}

	@Test
	public void TestDataStore() {
		Meter t = new Meter(1);

		PersistenceManager pm;

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
			pm.makePersistent(t);
		} finally {
			pm.close();
		}

		pm = PMF.getPersistenceManager();
		try {
			t = pm.getObjectById(Meter.class, 1);
		} finally {
			pm.close();
		}

		assertNotNull(pm);
		assertEquals(1, t.getNumber());
	}
	
	@Test
	public void testAddMeter() {
		
		meterService.getMeters(new AsyncCallback< List<Meter> >() {

			@Override
			public void onSuccess(List<Meter> result) {
				assertTrue(result.isEmpty());
				
			}

			@Override
			public void onFailure(Throwable caught) {
				fail("could not connect");
				
			}
		});
		fail("Not yet implemented");
		
	}

	@Test
	public void testRemoveMeter() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetMeters() {
		//fail("Not yet implemented");
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

}
