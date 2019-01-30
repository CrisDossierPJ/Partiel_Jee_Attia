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


@Stateless
@LocalBean
@Path("/products")
public class ProductService {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		List<Product> products = em.createQuery("select p from Product p").getResultList();
    		
			return  products;
		}
    }
	
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct(@PathParam("id") Long id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		TypedQuery<Product> query = em.createQuery("select p from Product p where p.id = :id", Product.class);
    		query.setParameter("id", id.intValue());
    		
    		Product product = query.getSingleResult();
    		
			return product;
		}
        
    }
    
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteProduct(@PathParam("id") int id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		Query query = em.createQuery("delete from Product where id = :id");
    		
    		query.setParameter("id", id).executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createProduct(Product product) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: création d'un utilisateur / ajouter un object user en paramètre
    		
    		em.persist(product);
    		
    		tx.commit();
    		
    	}
    }
    
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateUser(@PathParam("id") int id, Product product) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: ajouter un utilisateur en paramètre pour l'update
    		
    		
    		
    		Query query = em.createQuery("update Product p set"
    				+ "  p.name = :name"
    				+ ", p.price = :price"
    				+ "  where p.id = :id");
    		
    		query.setParameter("id", id);
    		query.setParameter("name", product.getName());
    		query.setParameter("price", product.getPrice());
    		
    		query.executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
	
    public ScopedEntityManager getScopedEntityManager() {
		return PersistenceManager.getInstance().getScopedEntityManagerFactory().createScopedEntityManager();
	}
}
