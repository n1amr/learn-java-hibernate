package com.n1amr.learn.hibernate.entities;


import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.HashSet;

@Entity(name = "amr_vehicles")
@Inheritance(strategy = InheritanceType.JOINED)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Vehicle {
	private int id;
	private String name;
	private Collection<User> rentingUsers = new HashSet<>();

	public Vehicle() {
	}

	public Vehicle(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "rentedVehicles")
	public Collection<User> getRentingUsers() {
		return rentingUsers;
	}

	public void setRentingUsers(Collection<User> rentingUsers) {
		this.rentingUsers = rentingUsers;
	}
}
