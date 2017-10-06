package com.mycompany.eCartBackEnd.daoImpl;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.eCartBackEnd.dao.UserDAO;
import com.mycompany.eCartBackEnd.model.User;


@EnableTransactionManagement
@Repository("userDAO")
public class UserDAOImpl implements UserDAO 
{
	private static final Logger log = LoggerFactory.getLogger(UserDAOImpl.class);
	
	@Autowired
	public SessionFactory sessionFactory;
	
	public UserDAOImpl(SessionFactory sessionFactory) 
	{
		try 
		{
			this.sessionFactory = sessionFactory;
			log.info("Connection Established Successfully");
		} 
		catch (Exception e) 
		{
			log.error("Failed to establish connection");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> list() 
	{
		log.debug("Starting of List Method");
		String hql_string = "FROM User";
		@SuppressWarnings({ "deprecation", "rawtypes"})
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		return query.list();
	}
	
	@Transactional
	public User getUser(String id) 
	{
		log.debug("Starting of Method Get");
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
		
	}

	@Transactional
	public User validateUser(String id, String password) 
	{
		log.debug("Starting of Method Validate ");
		String hql_string = "FROM User WHERE mail_id = '"+id+"' AND password = '"+password+"'";
		@SuppressWarnings("rawtypes")
		Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
		
		return (User) query.uniqueResult();
	}

	@Transactional
	public boolean addUser(User user) 
	{
		log.debug("Starting of Method Add User ");
		try
		{
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		}
		
		catch(Exception e)
		{	
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Transactional
	public boolean updateUser(User user) 
	{
		log.debug("Starting of Method Update User ");
		try
		{
			sessionFactory.getCurrentSession().update(user);
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Transactional
	public boolean deleteUser(String id) 
	{
		log.debug("Starting of Method Delete User ");
		try
		{
			String hql_string = "DELETE FROM User WHERE id = '"+id+"'";
			@SuppressWarnings("rawtypes")
			Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
			query.executeUpdate();
		}
		catch(Exception e)
		{
			System.out.println("failed");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}