package com.mycompany.eCartBackEnd.dao;

import java.util.List;
import com.mycompany.eCartBackEnd.model.SaveCart;

public interface SaveCartDAO 
{
	public String getBillId();
	
	public boolean addSaveCart(SaveCart saveCart);
	
	public List<SaveCart> getSavedCart(String bill_id);
	
	public List<String> getBills(String mail_id);
}
