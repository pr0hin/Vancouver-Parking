package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.ubc.cpsc310.vancouverparking.client.NotLoggedInException;

public class SearchHistoryImpl {
	
	private static final Logger LOG = Logger
			.getLogger(SearchHistoryImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public void addHistory(String location) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			SearchHistory sh = pm.getObjectById(SearchHistory.class, getUser()
					.getEmail());
			if(!sh.getHistory().contains(location))
				sh.addHistory(location);;
		} catch (Exception e){
			SearchHistory sh = new SearchHistory(getUser().getEmail());
			sh.addHistory(location);
			pm.makePersistent(sh);
		}finally {
			pm.close();
		}
	}


	public List<String> getHistory() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<String> historylist = new ArrayList<String>();
	
		try {
			SearchHistory sh = pm.getObjectById(SearchHistory.class, getUser()
					.getEmail());
			historylist.addAll(sh.getHistory());
		
		} catch (Exception e){
		} finally {
			pm.close();
		}
		System.out.println(historylist);
		return historylist;
	}

	private void checkLoggedIn() throws NotLoggedInException {
		if (getUser() == null) {
			throw new NotLoggedInException("Not logged in.");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}


