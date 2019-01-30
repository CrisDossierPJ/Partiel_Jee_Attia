	package com.christian.attia.partiel.database;

import javax.persistence.EntityManagerFactory;

public interface ScopedEntityManagerFactory extends EntityManagerFactory {
	public ScopedEntityManager createScopedEntityManager();
}
