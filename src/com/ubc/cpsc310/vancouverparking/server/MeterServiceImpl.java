package com.ubc.cpsc310.vancouverparking.server;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Iterator;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ubc.cpsc310.vancouverparking.client.MeterInfo;
import com.ubc.cpsc310.vancouverparking.client.MeterService;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MeterServiceImpl extends RemoteServiceServlet implements
		MeterService {

	private static final Logger LOG = Logger.getLogger(MeterServiceImpl.class
			.getName());

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public boolean loadMeters() {

		boolean res = false;
		// initializes an instance of the parser
		KMLParser parser = new KMLParser();
		List<Meter> meters = parser.parseFile();
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			// input all meters parsed into the datastore
			for (Meter m : meters) {
				pm.makePersistent(m);
			}
			// pm.makePersistentAll();
			res = true;
		} finally {
			pm.close();
		}
		return res;
	}

	public boolean removeMeters() {
		boolean res = false;
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			// ... do stuff with pm ...
			res = true;
		} finally {
			pm.close();
		}
		// TODO implement remove meters
		return res;
	}

	public List<MeterInfo> getMeters() {
		// simulates the parsing using the dataStub

		List<MeterInfo> metersInfo = new LinkedList<MeterInfo>();
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Meter> meters = new LinkedList<Meter>();

		try {
			// gets a list of all meters from the datastore
			
			Extent<Meter> extent = pm.getExtent(Meter.class, false);
			for (Meter meter : extent) {
				MeterInfo m = new MeterInfo();
				m.setNumber(meter.getNumber());
				m.setCreditCard(meter.isCreditCard());
				m.setLatitude(meter.getLatitude());
				m.setLongitude(meter.getLongitude());
				m.setType(meter.getType());
				m.setRate(meter.getRate());
				m.setTieEnd(meter.getTieEnd());
				m.setTieStart(meter.getTieStart());
				m.setTimeLimit(meter.getTimeLimit());
				metersInfo.add(m);
			}
			extent.closeAll();
//			Query q = pm.newQuery(Meter.class);
//			meters = (List<Meter>) q.execute();
//			if (meters != null) {
//				// translates meters into meterinfo
//				for (Meter meter : meters) {
//					MeterInfo m = new MeterInfo();
//					m.setNumber(meter.getNumber());
//					m.setCreditCard(meter.isCreditCard());
//					m.setLatitude(meter.getLatitude());
//					m.setLongitude(meter.getLongitude());
//					m.setType(meter.getType());
//					m.setRate(meter.getRate());
//					m.setTieEnd(meter.getTieEnd());
//					m.setTieStart(meter.getTieStart());
//					m.setTimeLimit(meter.getTimeLimit());
//					metersInfo.add(m);
//				}
		} finally {
			pm.close();
		}
		return metersInfo;
	}
}
