package com.mycompany.eCartBackEnd.dao;

import java.util.List;

import com.mycompany.eCartBackEnd.model.Cart;

public interface CartDAO 
{

	  public void addCart(Cart cart);

	  public void deleteCart(int id);
	  
	  public Cart getCart(String product_id);
	 
	  public List<Cart> list();
	  
	  public List<Cart> userCartList(String username);

	  public Cart getByIdCart(int id);
}
