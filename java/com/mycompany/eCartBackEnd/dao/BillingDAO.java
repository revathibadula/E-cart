package com.mycompany.eCartBackEnd.dao;

import com.mycompany.eCartBackEnd.model.Billing;

public interface BillingDAO 
{
	public boolean addBilling(Billing billing);
	
	public Billing getBilling(String mail_id);
	
	public String generateID();
}
