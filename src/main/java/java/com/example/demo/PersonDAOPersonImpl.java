package com.example.demo;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


@Repository
public class PersonDAOPersonImpl implements PersonDAO<Person>{
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public PersonDAOPersonImpl() {
		super();
	}
	
	@Override
	public List<Person> getAll(){
		List<Person>list = null;
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person>root=query.from(Person.class);
		query.select(root);
		list=(List<Person>)entityManager.createQuery(query).getResultList();
		return list;
	}
	
	@Override
	public List<Person> find(String fstr){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		query
			.select(root)
			.where(builder.equal(root.get("name"), fstr));
		List<Person>list = null;
		list = (List<Person>) entityManager
			.createQuery(query)
			.getResultList();
		return list;
	}
	
	@Override
	public List<Person> login_mail(String mail){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		query
			.select(root)
			.where(builder.equal(root.get("mail"), mail));
		List<Person>list = null;
		list = (List<Person>) entityManager
			.createQuery(query)
			.getResultList();
		return list;
	}
	
	@Override
	public List<Person> login_pass(String pass_O){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> query = builder.createQuery(Person.class);
		Root<Person> root = query.from(Person.class);
		query
			.select(root)
			.where(builder.equal(root.get("pass_O"), pass_O));
		List<Person>list = null;
		list = (List<Person>) entityManager
			.createQuery(query)
			.getResultList();
		return list;
	}
	
	@Override
	public Person findById(long id) {
		return (Person)entityManager.createQuery("from Person where id = " + id).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Person> findByName(String name){
		return (List<Person>)entityManager.createQuery("from Person where name = '"+ name + "'").getResultList();
	}
}