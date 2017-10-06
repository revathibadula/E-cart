package com.mycompany.eCartBackEnd.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

@Entity
@Table
@Component
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String mail_id;
	@NotEmpty(message = "Please Enter Your Name")
	private String f_name;
	@NotEmpty(message = "Please Enter Last Name")
	private String l_name;
	@Column(unique=true, nullable=false)
	private String mobile;
	private String password;
	
	private String role;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMail_id() 
	{
		return mail_id;
	}
	
	public void setMail_id(String mail_id) 
	{
		this.mail_id = mail_id;
	}
	
	public String getF_name() 
	{
		return f_name;
	}
	
	public void setF_name(String f_name) 
	{
		this.f_name = f_name;
	}
	
	public String getL_name() 
	{
		return l_name;
	}
	
	public void setL_name(String l_name) 
	{
		this.l_name = l_name;
	}
	
	public String getMobile() 
	{
		return mobile;
	}
	
	public void setMobile(String mobile) 
	{
		this.mobile = mobile;
	}
	
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}

}
