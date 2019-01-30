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
import com.christian.attia.partiel.database.entities.Product;
import com.christian.attia.partiel.database.entities.User;


@Stateless
@LocalBean
@Path("/users")
public class UserService {


	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllProducts() {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		List<User> users = em.createQuery("select u from User u").getResultList();
    		
			return  users;
		}
    }
	
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") Long id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		TypedQuery<User> query = em.createQuery("select u from User u where u.id = :id", User.class);
    		query.setParameter("id", id.intValue());
    		
    		User user = query.getSingleResult();
    		
			return user;
		}
        
    }
    
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") int id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		Query query = em.createQuery("delete from User where id = :id");
    		
    		query.setParameter("id", id).executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createUser(User user) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: création d'un utilisateur / ajouter un object user en paramètre
    		
    		em.persist(user);
    		
    		tx.commit();
    		
    	}
    }
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") int id, User user) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: ajouter un utilisateur en paramètre pour l'update

    		
    		Query query = em.createQuery("update User u set"
    				+ "  u.name = :name"
    				+ ", u.surname = :surname "
    				+ ", u.age = :age "
    				+ "where u.id = :id");
    		
    		query.setParameter("id", id);
    		query.setParameter("name", user.getName());
    		query.setParameter("surname", user.getSurname());
    		query.setParameter("age", user.getAge());

    		
    		query.executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
	
    public ScopedEntityManager getScopedEntityManager() {
		return PersistenceManager.getInstance().getScopedEntityManagerFactory().createScopedEntityManager();
	}
}
