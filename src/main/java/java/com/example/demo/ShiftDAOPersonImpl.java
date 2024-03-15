package com.example.demo;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ShiftDAOPersonImpl implements ShiftDAO<Shift>{
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public ShiftDAOPersonImpl() {
		super();
	}
}