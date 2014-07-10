package com.ubc.cpsc310.vancouverparking.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ubc.cpsc310.vancouverparking.client.FavoritesService;
import com.ubc.cpsc310.vancouverparking.client.NotLoggedInException;

public class FavoritesServiceImpl extends RemoteServiceServlet implements
		FavoritesService {

	private static final Logger LOG = Logger
			.getLogger(FavoritesServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	public void addMeter(Long number) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			Favorites fav = pm.getObjectById(Favorites.class, getUser()
					.getEmail());
			if(!fav.getFavoriteMeters().contains(number))
				fav.addMeter(number);
			LOG.log(Level.INFO, "meter " + number
					+ "added from favorites of user " + fav.getId());
		} catch (Exception e){
			Favorites fav = new Favorites(getUser().getEmail());
			fav.addMeter(number);
			pm.makePersistent(fav);
			LOG.log(Level.INFO, "meter " + number
					+ "added from favorites of user " + fav.getId());
		}finally {
			pm.refreshAll();
			pm.close();
		}
	}
	public void removeMeter(Long number) throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		try {
			Favorites fav = pm.getObjectById(Favorites.class, getUser()
					.getEmail());
			fav.removeMeter(number);
			LOG.log(Level.INFO, "meter " + number
					+ "removed from favorites of user " + fav.getId());
		} finally {
			pm.refreshAll();
			pm.close();
		}
	}

	public Long[] getFavorites() throws NotLoggedInException {
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<Long> listMeters = new ArrayList<Long>();
		Long[] favArray = new Long[0];
		try {
			Favorites fav = pm.getObjectById(Favorites.class, getUser()
					.getEmail());
			listMeters.addAll(fav.getFavoriteMeters());
			favArray = (Long[]) listMeters.toArray(new Long[0]);
		} catch (Exception e){
		} finally {
			pm.close();
		}
		System.out.println(listMeters);
		return favArray;
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
