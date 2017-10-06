package com.mycompany.eCartBackEnd.dao;

import com.mycompany.eCartBackEnd.model.Payment;

public interface PaymentDAO 
{

	public boolean addPaymentInfo(Payment payment);
	
	public Payment getPaymentInfo(String mail_id);
}
