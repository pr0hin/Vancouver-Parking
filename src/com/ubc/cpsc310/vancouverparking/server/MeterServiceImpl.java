package com.ubc.cpsc310.vancouverparking.server;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

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


	public void addMeter(Meter m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
	        
			// ... do stuff with pm ...
	    } finally {
	        pm.close();
	    }
		// TODO Auto-generated method stub
	}
	
	public void removeMeter(Meter m) {
		PersistenceManager pm = PMF.getPersistenceManager();
		try {
	        // ... do stuff with pm ...
	    } finally {
	        pm.close();
	    }
		// TODO Auto-generated method stub
	}

	public List<Meter> getMeters() {
		PersistenceManager pm = PMF.getPersistenceManager();
		List<Meter> meters = new LinkedList<Meter>();
		try {
			Query q = pm.newQuery(Meter.class);
			meters = (List<Meter>) q.execute();
	        // ... do stuff with pm ...
	    } finally {
	        pm.close();
	    }
		return meters;
	}
	
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
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			pm.makePersistent(new Stock(getUser(), symbol));
//		} finally {
//			pm.close();
//		}
//	}
//
//	public void removeStock(String symbol) throws NotLoggedInException {
//		checkLoggedIn();
//		PersistenceManager pm = getPersistenceManager();
//		try {
//			long deleteCount = 0;
//			Query q = pm.newQuery(Stock.class, "user == u");
//			q.declareParameters("com.google.appengine.api.users.User u");
//			List<Stock> stocks = (List<Stock>) q.execute(getUser());
//			for (Stock stock : stocks) {
//				if (symbol.equals(stock.getSymbol())) {
//					deleteCount++;
//					pm.deletePersistent(stock);
//				}
//			}
//			if (deleteCount != 1) {
//				LOG.log(Level.WARNING, "removeStock deleted " + deleteCount
//						+ " Stocks");
//			}
//		} finally {
//			pm.close();
//		}
//	}
//
//

}
