package com.mycompany.eCartBackEnd.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.eCartBackEnd.dao.CartDAO;
import com.mycompany.eCartBackEnd.model.Cart;

@Repository("cartDAO")
@EnableTransactionManagement
public class CartDAOImpl implements CartDAO 
{
	private static Logger log = LoggerFactory.getLogger("CartDAOImpl.class");
	
	@Autowired
	private SessionFactory sessionfactory;

	public CartDAOImpl(SessionFactory sessionfactory) 
	{
		this.sessionfactory = sessionfactory;
	}

	// used for transaction from model to DAO class
	@Transactional
	public void addCart(Cart cart) 
	{
		log.info("Add to Cart");
		sessionfactory.getCurrentSession().saveOrUpdate(cart);
	}

	@Transactional
	public void deleteCart(int id) 
	{
		log.info("Delete Cart Initiated.");
		Cart cart = new Cart();
		cart.setCart_id(id);
		sessionfactory.getCurrentSession().delete(cart);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Cart getCart(String u_id) 
	{
		log.info("Get Cart by User ID "+u_id);
		String hql = "from Cart where mail_id=" + "'" + u_id + "'";
		Query query = sessionfactory.getCurrentSession().createQuery(hql);
		List<Cart> list = (List<Cart>) query.list();
		if (list != null && !list.isEmpty()) 
		{
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
	public Cart getByIdCart(int id) 
	{
		log.info("Get Cart by ID "+id);
		String hql = "from Cart where cart_id = "+id;
		Query query = sessionfactory.getCurrentSession().createQuery(hql);
		List<Cart> list = (List<Cart>) query.list();
		if (list != null && !list.isEmpty()) 
		{
			return list.get(0);
		}
		return null;
	}

	@Transactional
	public List<Cart> list() 
	{
		log.info("List Full Cart");
		@SuppressWarnings("unchecked")
		List<Cart> list = (List<Cart>) sessionfactory.getCurrentSession().createCriteria(Cart.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
		return list;
	}	

	@Transactional
	public List<Cart> userCartList(String username) 
	{
		log.info("get Cart by User ID "+username);
		String hql = "from Cart where mail_id=" + "'" + username + "'";
		@SuppressWarnings("rawtypes")
		Query query = sessionfactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<Cart> list = query.list();
		if (list != null && !list.isEmpty()) 
		{
			return list;
		}
		return null;
	}
}