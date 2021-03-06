package com.christian.attia.partiel.database.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * Product Order Entity
 * @author Christian
 *
 */
@Entity
@Table(name="productOrder")
public class ProductOrder {
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonBackReference
	@JoinColumn(name="product_id")
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Product product;
	
	@JsonBackReference
	@JoinColumn(name="order_id")
	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private Order order;
	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 
	 * @return
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * 
	 * @param product
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * 
	 * @return
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * 
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
	
}
