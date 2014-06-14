package com.ubc.cpsc310.vancouverparking.server;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.ubc.cpsc310.vancouverparking.client.MeterInfo;
import com.ubc.cpsc310.vancouverparking.client.MeterService;
import com.ubc.cpsc310.vancouverparking.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

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


//	public String[] getStocks() throws NotLoggedInException {
//		checkLoggedIn();
//		PersistenceManager pm = getPersistenceManager();
//		List<String> symbols = new ArrayList<String>();
//		try {
//			Query q = pm.newQuery(Stock.class, "user == u");
//			q.declareParameters("com.google.appengine.api.users.User u");
//			q.setOrdering("createDate");
//			List<Stock> stocks = (List<Stock>) q.execute(getUser());
//			for (Stock stock : stocks) {
//				symbols.add(stock.getSymbol());
//			}
//		} finally {
//			pm.close();
//		}
//		return (String[]) symbols.toArray(new String[0]);
//	}
//	public void addStock(String symbol) throws NotLoggedInException {
//		checkLoggedIn();

//	public void addMeter() {
//		MeterDataStub metersStub = new MeterDataStub();

//		PersistenceManager pm = getPersistenceManager();
//		try {
//
//			pm.makePersistentAll(metersStub.getMetersList());
//		} finally {
//			pm.close();
//		}
//		// TODO Auto-generated method stub
//	}

//
//


//	public void removeMeter(MeterInfo m) {
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			// ... do stuff with pm ...
//		} finally {
//			pm.close();
//		}
//		// TODO Auto-generated method stub
//	}


	public List<MeterInfo> getMeters() {
		MeterDataStub metersStub = new MeterDataStub();
		//PersistenceManager pm = getPersistenceManager();
		// List<Meter> meters = new LinkedList<Meter>();
		List<Meter> meters = metersStub.getMetersList();
		List<MeterInfo> metersInfo = new LinkedList<MeterInfo>();
		// try {
		//
		// Query q = pm.newQuery(Meter.class);
		//
		// meters = (List<Meter>) q.execute();
		//
		// for(Meter meter : meters) {
		// MeterInfo m = new MeterInfo(meter.getNumber());
		// m.setCreditCard(true);
		// m.setLatitude(m.getLatitude());
		// m.setLongitude(m.getLongitude());
		// metersInfo.add(m);
		// }
		//
		//
		//
		// } finally {
		// pm.close();
		// }

		for (Meter meter : meters) {
			MeterInfo m = new MeterInfo();
			m.setNumber(meter.getNumber());
			m.setCreditCard(true);
			m.setLatitude(meter.getLatitude());
			m.setLongitude(meter.getLongitude());
			m.setType("bla");
			m.setRate(2.00);
			metersInfo.add(m);
		}
		// TODO Auto-generated method stub
		return metersInfo;
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}

	// public void addStock(String symbol) throws NotLoggedInException {
	// checkLoggedIn();
	// PersistenceManager pm = getPersistenceManager();
	// try {
	// pm.makePersistent(new Stock(getUser(), symbol));
	// } finally {
	// pm.close();
	// }
	// }
	//
	// public void removeStock(String symbol) throws NotLoggedInException {
	// checkLoggedIn();
	// PersistenceManager pm = getPersistenceManager();
	// try {
	// long deleteCount = 0;
	// Query q = pm.newQuery(Stock.class, "user == u");
	// q.declareParameters("com.google.appengine.api.users.User u");
	// List<Stock> stocks = (List<Stock>) q.execute(getUser());
	// for (Stock stock : stocks) {
	// if (symbol.equals(stock.getSymbol())) {
	// deleteCount++;
	// pm.deletePersistent(stock);
	// }
	// }
	// if (deleteCount != 1) {
	// LOG.log(Level.WARNING, "removeStock deleted " + deleteCount
	// + " Stocks");
	// }
	// } finally {
	// pm.close();
	// }
	// }
	//
	// public String[] getStocks() throws NotLoggedInException {
	// checkLoggedIn();
	// PersistenceManager pm = getPersistenceManager();
	// List<String> symbols = new ArrayList<String>();
	// try {
	// Query q = pm.newQuery(Stock.class, "user == u");
	// q.declareParameters("com.google.appengine.api.users.User u");
	// q.setOrdering("createDate");
	// List<Stock> stocks = (List<Stock>) q.execute(getUser());
	// for (Stock stock : stocks) {
	// symbols.add(stock.getSymbol());
	// }
	// } finally {
	// pm.close();
	// }
	// return (String[]) symbols.toArray(new String[0]);
	// }

}
