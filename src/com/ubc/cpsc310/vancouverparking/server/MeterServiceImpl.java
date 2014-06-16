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
import javax.jdo.Transaction;

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
		// removeMeters();
		PersistenceManager pm = PMF.getPersistenceManager();
		//Transaction tx = pm.currentTransaction();
		try {
			// tx.begin();
			// input all meters parsed into the datastore
			pm.makePersistentAll(meters);
			res = true;
			// tx.commit();
		} finally {
			// while(tx.isActive()){
			// }
			// if (tx.isActive()) {
			// tx.rollback(); // Error occurred so rollback the PM transaction
			// }
			pm.refreshAll();
			pm.close();
		}
		System.out.println("Meters to load :" + meters.size());
		if (!pm.isClosed()){
			pm.close();
		}
		pm = PMF.getPersistenceManager();
		List<Meter> metersParsed = new LinkedList<Meter>();
		try {

//			Query q = pm.newQuery(Meter.class);
//			metersParsed = (List<Meter>) q.execute();
			Query q = pm.newQuery(Meter.class);//, "city == c");
			//q.declareParameters("String c");
			metersParsed = (List<Meter>) q.execute();

		} finally {
			pm.close();
		}
			System.out.println("Meters loaded :" + metersParsed.size());
		return res;
	}

	public boolean removeMeters() {
		boolean res = false;
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
//			Extent<Meter> extent = pm.getExtent(Meter.class, true);
//			for (Meter meter : extent) {
//				pm.deletePersistent(meter);
//			}
			Query q = pm.newQuery(Meter.class);//, "city == c");
			//q.declareParameters("String c");
			q.deletePersistentAll();
			res = true;
		} finally {
			pm.close();
		}
		pm = PMF.getPersistenceManager();
		pm.refreshAll();
		try {
			Query q = pm.newQuery(Meter.class);//, "city == c");
			//q.declareParameters("String c");
			List<Meter> meters = (List<Meter>) q.execute();
			System.out.println("Meters remained :" + meters.size());

		} finally {
			pm.close();
		}
		return res;
	}

	public List<MeterInfo> getMeters() {
		// simulates the parsing using the dataStub

		List<MeterInfo> metersInfo = new LinkedList<MeterInfo>();
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Meter> meters = new LinkedList<Meter>();

		try {
			// gets a list of all meters from the datastore

			// Extent<Meter> extent = pm.getExtent(Meter.class, true);
			// for (Meter meter : extent) {
			// MeterInfo m = new MeterInfo();
			// m.setNumber(meter.getNumber());
			// m.setCreditCard(meter.isCreditCard());
			// m.setLatitude(meter.getLatitude());
			// m.setLongitude(meter.getLongitude());
			// m.setType(meter.getType());
			// m.setRate(meter.getRate());
			// m.setTieEnd(meter.getTieEnd());
			// m.setTieStart(meter.getTieStart());
			// m.setTimeLimit(meter.getTimeLimit());
			// metersInfo.add(m);
			// System.out.println(metersInfo.size());
			// }
			// extent.closeAll();
			Query q = pm.newQuery(Meter.class);//, "city == c");
			//q.declareParameters("String c");
			meters = (List<Meter>) q.execute();

		} finally {
			pm.close();
		}
		System.out.println("Meters retrieved :" + meters.size());
		if (meters != null) {
			// translates meters into meterinfo
			for (Meter meter : meters) {
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
		}
		return metersInfo;
	}
}
