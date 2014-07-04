package com.ubc.cpsc310.vancouverparking;

import static org.junit.Assert.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import org.junit.Before;
import org.junit.Test;

import com.ubc.cpsc310.vancouverparking.server.Favorites;

public class FavoritesTest {

	private String user1Email;
	private String user2Email;
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
	
	@Before
	public void begin() {
		this.user1Email = "fulano@gmail.com";
		this.user2Email = "ciclano@gmail.com";
		Favorites f1 = new Favorites(user1Email);
		Favorites f2 = new Favorites(user2Email);
		PersistenceManager pm = PMF.getPersistenceManager();

		try {
			// input all meters parsed into the datastore
			pm.makePersistent(f1);

		} finally {
			pm.close();
		}
	}
	
	@Test
	public void testPersistence() {
		PersistenceManager pm = PMF.getPersistenceManager();

		try {
			// input all meters parsed into the datastore
			Favorites test1 = (Favorites) pm.getObjectById(user1Email);
			assertTrue(test1.getId().equals(user1Email));

		} finally {
			pm.close();
		}
	}

}
