package com.christian.attia.partiel.services;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.christian.attia.partiel.database.PersistenceManager;
import com.christian.attia.partiel.database.ScopedEntityManager;
import com.christian.attia.partiel.database.entities.Currency;
import com.christian.attia.partiel.database.entities.Product;


@Stateless
@LocalBean
@Path("/currencies")
public class CurrencyService {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Currency> getAllCurrencies() {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		List<Currency> currency = em.createQuery("select c from Currency c").getResultList();
    		
			return  currency;
		}
    }
	
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Currency getCurrency(@PathParam("id") Long id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		TypedQuery<Currency> query = em.createQuery("select c from Currency c where c.id = :id", Currency.class);
    		query.setParameter("id", id.intValue());
    		
    		Currency currency = query.getSingleResult();
    		
			return currency;
		}
        
    }
    
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCurrency(@PathParam("id") int id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		Query query = em.createQuery("delete from Currency where id = :id");
    		
    		query.setParameter("id", id).executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createCurrency(Currency currency) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: création d'un utilisateur / ajouter un object user en paramètre
    		
    		em.persist(currency);
    		
    		tx.commit();
    		
    	}
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateCurrency(@PathParam("id") int id, Currency currency) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: ajouter un utilisateur en paramètre pour l'update
    		
    		Query query = em.createQuery("update Currency c set"
    				+ "  c.lib_currency = :lib_currency where c.id = :id" 
    				);
    		
    		query.setParameter("id", id);
    		query.setParameter("lib_currency", currency.getLib_currency());


    		
    		query.executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
	
    public ScopedEntityManager getScopedEntityManager() {
		return PersistenceManager.getInstance().getScopedEntityManagerFactory().createScopedEntityManager();
	}
}
