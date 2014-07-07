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
import static javax.jdo.FetchPlan.FETCH_SIZE_OPTIMAL;

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

	public void loadMeters() {

		// initializes an instance of the parser
		KMLParser parser = new KMLParser();
		List<Meter> meters = parser.parse();
		
		//List<Meter> meters = new MeterDataStub().getMetersList();
		//removeMeters();

		PersistenceManager pm = PMF.getPersistenceManager();

		try {
			// input all meters parsed into the datastore
			pm.makePersistentAll(meters);

		} finally {
			pm.refreshAll();
			pm.close();
		}
	}

	public void removeMeters() {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
			Query q = pm.newQuery(Meter.class);
			q.deletePersistentAll();
		} finally {
			pm.close();
		}
	}

	
	//TODO change the return of this function to Meter[]
	public List<MeterInfo> getMeters() {
		
		
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Meter> meters = new LinkedList<Meter>();
		//KMLParser parser = new KMLParser();
		//meters = parser.parse();

		try {
			// gets a list of all meters from the datastore
			Query q = pm.newQuery(Meter.class);
			q.getFetchPlan().setFetchSize(1000);
			meters = (List<Meter>) q.execute();

		} finally {
			pm.close();
		}
		System.out.println("Meters retrieved :" + meters.size());

		return parseMetertoMeterInfo(meters);
	}

	private List<MeterInfo> parseMetertoMeterInfo(List<Meter> meters) {
		List<MeterInfo> metersInfo = new LinkedList<MeterInfo>();
		if (meters != null) {
			// translates meters into meterinfo
			for (Meter meter : meters) {
				MeterInfo m = new MeterInfo();
				m.setNumber(meter.getNumber());
				m.setCreditCard(meter.isCreditCard());
				m.setLatitude(meter.getGeopoint().getLatitude());
				m.setLongitude(meter.getGeopoint().getLongitude());
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
