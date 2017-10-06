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

import com.mycompany.eCartBackEnd.dao.SupplierDAO;
import com.mycompany.eCartBackEnd.model.Supplier;

@Repository("supplierDAO")
@EnableTransactionManagement
public class SupplierDAOImpl implements SupplierDAO
{
	private static Logger log = LoggerFactory.getLogger(SupplierDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	public SupplierDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public boolean addSupplier(Supplier supplier) 
	{
		log.info("Add supplier method started.");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(supplier);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Transactional
	public boolean updateSupplier(Supplier supplier) 
	{
		log.info("Update supplier method started");
		try 
		{
			sessionFactory.getCurrentSession().update(supplier);
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}	
		return true;
	}

	@Transactional
	public boolean deleteSupplier(String id) 
	{
		log.info("Delete supplier method started");
		try 
		{
			String hql_string = "DELETE FROM Supplier WHERE id = '"+id+"'";
			@SuppressWarnings("rawtypes")
			Query query = sessionFactory.getCurrentSession().createQuery(hql_string);
			query.executeUpdate();
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<Supplier> listSupplier() 
	{
		log.info("Get Supplier Method started");
		String hql = "FROM Supplier";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}

	@Transactional
	public Supplier getSupplier(String id) 
	{
		return (Supplier) sessionFactory.getCurrentSession().get(Supplier.class, id);}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public Supplier getByName(String name) 
	{
		try 
		{
			log.debug("GetByName method Started");
			String sql = "FROM Supplier where name='"+name+"'";
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			List<Supplier> list = query.list(); 
			return list.get(0);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.debug("GetByName error");
			return null;
		}
	}
}