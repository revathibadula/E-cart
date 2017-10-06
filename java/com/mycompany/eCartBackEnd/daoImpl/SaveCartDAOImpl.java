package com.mycompany.eCartBackEnd.daoImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.eCartBackEnd.dao.SaveCartDAO;
import com.mycompany.eCartBackEnd.model.SaveCart;

@Repository("saveCartDAO")
@EnableTransactionManagement
public class SaveCartDAOImpl implements SaveCartDAO
{
	Logger log = LoggerFactory.getLogger(SaveCartDAOImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SaveCartDAOImpl(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unused")
	@Transactional
	public String getBillId()
	{
		log.info("Generate Bill ID");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy_MM_dd");
		String localDate = LocalDate.now().toString();
		String uuid = UUID.randomUUID().toString();
		String bill_id = "MHG-"+localDate+uuid;
		return bill_id;
	}

	@Transactional
	public boolean addSaveCart(SaveCart saveCart)
	{
		log.info("Save to Cart Oder initated");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(saveCart);
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
	public List<SaveCart> getSavedCart(String bill_id) 
	{
		log.info("Save Cart Retrieve from bill id initiated.");
		String sql = "FROM SaveCart where bill_id = "+"'"+bill_id+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		List<SaveCart> list = query.list();
		if (list != null && !list.isEmpty()) 
		{
			return list;
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional
	public List<String> getBills(String mail_id)
	{
		log.info("Get bills for user initiated");
		String sql = " FROM SaveCart where mail_id = '"+mail_id+"'";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		List<SaveCart> list = query.list();
		if (list != null && !list.isEmpty())
		{
			Set<String> bills = new HashSet<String>();
			int n = list.size();
			for(int i=0; i<n; i++)
			{
				String bill = list.get(i).getBill_id();
				bills.add(bill);
			}
			List<String> bill_list = new ArrayList();
			bill_list.clear();
			bill_list.addAll(bills);
			return bill_list;
		}
		return null;
	}

}
