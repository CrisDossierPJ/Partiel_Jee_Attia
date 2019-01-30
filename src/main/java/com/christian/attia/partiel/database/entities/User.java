package com.christian.attia.partiel.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 	User Entity
 * @author Christian
 *
 */
@Entity
@Table(name="\"user\"")
public class User {
	
	@Id
	@Column(name="id", nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="surname", nullable=false)
	private String surname;
	
	@Column(name="age", nullable=false)
	private Long age;
	
	/**
	 * constructeur
	 */
	public User() {

		this.name = "";
		this.surname = "";
		this.age = (long) 0;
	}
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
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * @return
	 */
	public String getSurname() {
		return surname;
	}
	/**
	 * 
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	/**
	 * 
	 * @return
	 */
	public Long getAge() {
		return age;
	}
	/**
	 * 
	 * @param age
	 */
	public void setAge(Long age) {
		this.age = age;
	}
	
	
}
