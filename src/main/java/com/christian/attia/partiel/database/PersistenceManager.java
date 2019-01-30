package com.christian.attia.partiel.database;

import java.lang.reflect.Proxy;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	private static final PersistenceManager singleton = new PersistenceManager();

	protected ScopedEntityManagerFactory semf;

	public static PersistenceManager getInstance() {
		return singleton;
	}

	private PersistenceManager() {
	}

	public ScopedEntityManagerFactory getScopedEntityManagerFactory() {
		
		if (semf == null) {
			createScopedEntityManagerFactory();
		}
		return semf;
	}

	public void closeEntityManagerfactory() {
		if (semf != null) {
			semf.close();
			semf = null;
		}
	}

	private void createScopedEntityManagerFactory() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("partiel");
		semf = (ScopedEntityManagerFactory) Proxy.newProxyInstance(ScopedEntityManagerFactory.class.getClassLoader(),
				new Class[] { ScopedEntityManagerFactory.class }, new EntityManagerFactoryHandler(emf));
	}
}
