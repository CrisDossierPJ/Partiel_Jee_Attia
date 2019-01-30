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
import com.christian.attia.partiel.database.entities.Order;
import com.christian.attia.partiel.database.entities.Product;
import com.christian.attia.partiel.database.entities.ProductOrder;


@Stateless
@LocalBean
@Path("/productorders")
public class ProductOrderService {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductOrder> getAllProductOrders() {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		List<ProductOrder> orders = em.createQuery("select p from ProductOrder p ").getResultList();
    		
			return  orders;
		}
    }
	
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductOrder getProductOrder(@PathParam("id") Long id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		TypedQuery<ProductOrder> query = em.createQuery("select p from ProductOrder p where p.id = :id", ProductOrder.class);
    		query.setParameter("id", id.intValue());
    		
    		ProductOrder productOrder = query.getSingleResult();
    		
			return productOrder;
		}
        
    }
    
    
    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteProductOrder(@PathParam("id") int id) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		Query query = em.createQuery("delete from ProductOrder where id = :id");
    		
    		query.setParameter("id", id).executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
    

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createProductOrder(ProductOrder productOrder) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: création d'un utilisateur / ajouter un object user en paramètre
    		
    		em.persist(productOrder);
    		
    		tx.commit();
    		
    	}
    }
    
    
    @PUT
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateProductOrder(@PathParam("id") int id, ProductOrder productOrder) {
    	try (ScopedEntityManager em = getScopedEntityManager()) {
    		
    		EntityTransaction tx = em.getTransaction();

    		tx.begin();
    		
    		// TODO: ajouter un utilisateur en paramètre pour l'update
    		
    		
    		
    		Query query = em.createQuery("update ProductOrder p set"
    				+ " p.order_id = :order_id"
    				+ "  p.product_id = :product_id"
    				+ "  where p.id = :id");
    		
    		query.setParameter("id", id);
    		query.setParameter("order_id", productOrder.getOrder());
    		query.setParameter("product_id", productOrder.getProduct());

    		
    		query.executeUpdate();
    		
    		tx.commit();
    		
    	}
    }
	/**
	 * Création de l'instance de l'entity manager
	 * @return
	 */
    public ScopedEntityManager getScopedEntityManager() {
		return PersistenceManager.getInstance().getScopedEntityManagerFactory().createScopedEntityManager();
	}
}
