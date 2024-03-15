package com.example.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
public class Person{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	@NotNull
	private long id;
	
	@Column(length=50, nullable=false)
	@NotBlank
	private String name;
	
	@Column(length=50, nullable=false)
	@Email
	private String mail;
	
	@Column(length=50, nullable=false)
	@Size(min=5, max=20)
	private String pass_O;
	
	@Column(length=50, nullable=false)
	@Size(min=5, max=20)
	private String pass_A;
	
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	
	public String getMail() {return mail;}
	public void setMail(String mail) {this.mail = mail;}
	
	public String getPass_O() {return pass_O;}
	public void setPass_O(String pass_O) {this.pass_O = pass_O;}
	
	public String getPass_A() {return pass_A;}
	public void setPass_A(String pass_A) {this.pass_A = pass_A;}
}