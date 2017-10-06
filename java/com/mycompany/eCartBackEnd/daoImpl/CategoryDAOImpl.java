package com.mycompany.eCartBackEnd.daoImpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.model.Category;

@Repository("categoryDAO")
@EnableTransactionManagement
public class CategoryDAOImpl implements CategoryDAO
{
	private final static Logger log = LoggerFactory.getLogger(CategoryDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;

	public CategoryDAOImpl(SessionFactory sessionFactory)
	{
		try 
		{
			this.sessionFactory = sessionFactory;
			log.info("Connection is successfully established");
		}
		catch (Exception e) 
		{
			log.error("Unable to establish H2 connection, check Application Context file");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Category> listCategory() 
	{
		log.debug("List Category method initiated");
		try 
		{
			String hql_string = "FROM Category";
			@SuppressWarnings("rawtypes")
			Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
			log.debug("List Category method success");
			return query.list();
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.error("List Category method error");
			return null;
		}
	}

	@Transactional
	public boolean addCategory(Category category) 
	{
		log.debug("Add Category method initiated");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(category);
			log.debug("Add Category method success");
		}
		catch (HibernateException e) 
		{	
			e.printStackTrace();
			log.error("Add Category method error");
			return false;
		}
		return true;
	}
	
	@Transactional
	public boolean updateCategory(Category category)
	{
		log.debug("Update Category method initiated");
		try 
		{
			sessionFactory.getCurrentSession().update(category);
			log.debug("Update Category method success");
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.error("Update Category method error");
			return false;
		}
		return true;
	}

	@Transactional
	public boolean deleteCategory(String id) 
	{
		log.debug("Delete Category method initiated");
		try
		{
			String hql = "DELETE FROM Category WHERE id = '"+id+"'";
			@SuppressWarnings({ "rawtypes", "deprecation" })
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			log.debug("Delete Category method success");
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			log.error("Delete Category method error");
			return false;
		}
		return true;
	}

	@Transactional
	public Category getCategory(String id) 
	{
		log.debug("Get Category method initiated");
		try 
		{
			log.debug("Get Category method success");
			return (Category) sessionFactory.getCurrentSession().get(Category.class, id);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.debug("Get Category method error");
			return null;
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Category getByName(String name)
	{
		try 
		{
			log.debug("GetByName method Started");
			String sql = "FROM Category where name='"+name+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			List<Category> list = query.list(); 
			return list.get(0);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.debug("GetByName error.");
			return null;
		}
		
	}
}