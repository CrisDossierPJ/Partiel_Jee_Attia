package com.christian.attia.partiel.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerFactoryHandler implements InvocationHandler {

	private static final Method createEntityManager;
	private static final Method createScopedEntityManager;
	private static final Logger logger;
	private final EntityManagerFactory emf;
	
	static {
		try {
			createEntityManager = EntityManagerFactory.class.getMethod("createEntityManager");
			createScopedEntityManager = ScopedEntityManagerFactory.class.getMethod("createScopedEntityManager");
			logger = Logger.getLogger(ScopedEntityManagerFactory.class.getCanonicalName());
		} catch(NoSuchMethodException e) {
			throw new ExceptionInInitializerError(e);
		}

	}
	
	public EntityManagerFactoryHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	@Override
	public Object invoke(Object o, Method method, Object[] os) throws Throwable {
		logger.entering(ScopedEntityManagerFactory.class.getCanonicalName(), method.getName(), os);
		
		Object result;
		
		try {
			if(method.equals(createScopedEntityManager)) {
				assert(os.length == 0);
				EntityManager target = (EntityManager) createEntityManager.invoke(emf, os);
				result = Proxy.newProxyInstance(
						ScopedEntityManagerFactory.class.getClassLoader()
						, new Class[] {ScopedEntityManager.class}
						, new EntityManagerHandler(target));
			} else {
				result = method.invoke(emf, os);
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

}
