package com.christian.attia.partiel.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="currency")
public class Currency {
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="lib_currency", nullable=false, unique=true)
	private String lib_currency;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLib_currency() {
		return lib_currency;
	}

	public void setLib_currency(String lib_currency) {
		this.lib_currency = lib_currency;
	}
	
		
	
}
