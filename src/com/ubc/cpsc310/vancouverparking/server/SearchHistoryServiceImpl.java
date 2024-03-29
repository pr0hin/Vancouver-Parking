package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ubc.cpsc310.vancouverparking.client.NotLoggedInException;
import com.ubc.cpsc310.vancouverparking.client.SearchHistoryService;

public class SearchHistoryServiceImpl extends RemoteServiceServlet implements
		SearchHistoryService {

	private static final Logger LOG = Logger
			.getLogger(SearchHistoryServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public void addHistory(String location) {

		// checkLoggedIn();
		if (getUser() != null) {

			PersistenceManager pm = getPersistenceManager();
			try {
				SearchHistory sh = pm.getObjectById(SearchHistory.class,
						getUser().getEmail());
				if (!sh.getHistory().contains(location)) {
					sh.addHistory(location);
				}
			} catch (Exception e) {

				SearchHistory sh = new SearchHistory(getUser().getEmail());
				sh.addHistory(location);
				pm.makePersistent(sh);

			} finally {
				pm.close();
			}
		}
	}

	public List<String> getHistory() {
		List<String> historylist = new ArrayList<String>();
		if (getUser() != null) {
			PersistenceManager pm = getPersistenceManager();
			

			try {
				SearchHistory sh = pm.getObjectById(SearchHistory.class,
						getUser().getEmail());
				historylist.addAll(sh.getHistory());

			} catch (Exception e) {

			} finally {
				pm.close();
			}
			System.out.println(historylist);
		}
		return historylist;
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
