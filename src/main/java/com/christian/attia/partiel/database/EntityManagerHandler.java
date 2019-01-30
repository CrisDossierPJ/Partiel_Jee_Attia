package com.christian.attia.partiel.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class EntityManagerHandler implements InvocationHandler {

	private static final Method executeTransaction;
	private static final Method executeVoidTransaction;
	private static final Logger logger;
	private final EntityManager em;

	static {
		try {
			executeTransaction = ScopedEntityManager.class.getMethod("executeTransaction",
					ScopedEntityManager.TransactionFunction.class);
			executeVoidTransaction = ScopedEntityManager.class.getMethod("executeTransaction",
					ScopedEntityManager.Transaction.class);
			logger = Logger.getLogger(ScopedEntityManagerFactory.class.getCanonicalName());
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}

	}

	public EntityManagerHandler(EntityManager em) {
		this.em = em;
	}

	@Override
	public Object invoke(Object o, Method method, Object[] os) throws Throwable {
		logger.entering(ScopedEntityManagerFactory.class.getCanonicalName(), method.getName(), os);
		
		Object result;
		
		try {
			if(method.equals(executeTransaction) || method.equals(executeVoidTransaction)) {
				assert(os.length == 1);
				result = proxyExecuteTransaction(os[0]);
			} else {
				result = method.invoke(em, os);
			}
		} catch (Exception e) {
			logger.throwing(ScopedEntityManagerFactory.class.getCanonicalName(), method.getName(), e);
			throw e;
		}
		
		if (method.getReturnType().getClass().equals(Void.class.getClass())) {
			logger.exiting(ScopedEntityManagerFactory.class.getCanonicalName(), method.getName());
		} else {
			logger.exiting(ScopedEntityManagerFactory.class.getCanonicalName(), method.getName(), result);
		}
		
		return result;
	}
	
	
	protected Object proxyExecuteTransaction(Object fn) {
		EntityTransaction t = em.getTransaction();
		Object result = null;
		
		try {
			t.begin();
			if(fn instanceof ScopedEntityManager.Transaction) {
				((ScopedEntityManager.Transaction) fn).execute();
			} else if(fn instanceof ScopedEntityManager.TransactionFunction) {
				result = ((ScopedEntityManager.TransactionFunction) fn).execute();
			} else {
				assert(false);
			}
			t.commit();
		} finally {
			if(t.isActive()) {
				t.rollback();
			}
		}
		return result;
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			if(em.isOpen()) {
				logger.warning("closing entity manager by finalizer !");
			} 
		} finally {
			super.finalize();
		}
	}
	

}
