package com.example.demo;

import java.io.Serializable;
import java.util.List;

public interface PersonDAO <T> extends Serializable {
	public List<T> getAll();
	public T findById(long id);
	public List<T> findByName(String name);
	public List<T> find(String fstr);
	public List<T> login_mail(String mail);
	public List<T> login_pass(String pass_O);
}
