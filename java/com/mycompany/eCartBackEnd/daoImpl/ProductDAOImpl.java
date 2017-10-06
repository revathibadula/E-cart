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

import com.mycompany.eCartBackEnd.dao.ProductDAO;
import com.mycompany.eCartBackEnd.model.Product;

@Repository("productDAO")
@EnableTransactionManagement
public class ProductDAOImpl implements ProductDAO
{
	Logger log = LoggerFactory.getLogger(ProductDAOImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	public ProductDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public boolean addProduct(Product product) 
	{
		log.info("Add Product initiated");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(product);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateProduct(Product product) 
	{
		log.info("Update Product called");
		try 
		{
			sessionFactory.getCurrentSession().update(product);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	public boolean deleteProduct(String id) 
	{
		log.info("Delete Product initiated");
		try 
		{
			String hql = "DELETE FROM Product WHERE id = '"+id+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.executeUpdate();
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public List<Product> listProduct() 
	{
		log.info("List all products");
		String hql = "FROM Product";
		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public Product getProduct(String id) 
	{
		log.info("getProduct initiated");
		return (Product) sessionFactory.getCurrentSession().get(Product.class, id);}

}