package com.mycompany.eCartBackEnd.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.eCartBackEnd.dao.PaymentDAO;
import com.mycompany.eCartBackEnd.model.Payment;

@Repository("paymentDAO")
@EnableTransactionManagement
public class PaymentDAOImpl implements PaymentDAO
{
	private static final Logger log = LoggerFactory.getLogger(PaymentDAOImpl.class);
	@Autowired
	SessionFactory sessionFactory;
	
	public PaymentDAOImpl(SessionFactory sessionFactory)
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

	@Transactional
	public boolean addPaymentInfo(Payment payment) 
	{
		log.debug("Add PaymentInfo method initiated");
		try 
		{
			sessionFactory.getCurrentSession().saveOrUpdate(payment);
			log.debug("Add PaymentInfo method success");
		}
		catch (HibernateException e) 
		{	
			e.printStackTrace();
			log.error("Add PaymentInfo method error");
			return false;
		}
		return true;
	}

	@Transactional
	public Payment getPaymentInfo(String mail_id)
	{
		log.debug("Get PaymentInfo method initiated");
		try 
		{
			log.debug("Get PaymentInfo method success");
			return (Payment) sessionFactory.getCurrentSession().get(Payment.class, mail_id);
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			log.debug("Get PaymentInfo method error");
			return null;
		}
	}
	
	
}
