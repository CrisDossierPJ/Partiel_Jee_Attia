package com.christian.attia.partiel.database.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Order Entity
 * @author Christian
 *
 */

@Entity
@Table(name="\"order\"")
public class Order {
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@JsonBackReference
	@JoinColumn(name="user_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	private User user;
	
	/**
	 * 
	 * @return
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	



}
