package com.mycompany.eCart.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.eCartBackEnd.dao.BillingDAO;
import com.mycompany.eCartBackEnd.dao.CartDAO;
import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.dao.PaymentDAO;
import com.mycompany.eCartBackEnd.dao.ProductDAO;
import com.mycompany.eCartBackEnd.dao.UserDAO;
import com.mycompany.eCartBackEnd.model.Billing;
import com.mycompany.eCartBackEnd.model.Cart;
import com.mycompany.eCartBackEnd.model.Category;
import com.mycompany.eCartBackEnd.model.Payment;
import com.mycompany.eCartBackEnd.model.Product;

@Controller
public class CartController 
{
	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	@Autowired(required = true)
	private Cart cart;

	@Autowired(required = true)
	private CartDAO cartDAO;

	@Autowired(required = true)
	private CategoryDAO categoryDAO;

	@Autowired(required = true)
	private ProductDAO productDAO;

	@Autowired(required = true)
	private Product product;

	@Autowired(required = true)
	UserDAO userDAO;
	
	@Autowired(required = true)
	Payment payment;
	
	@Autowired(required = true)
	BillingDAO billingDAO;
	
	@Autowired(required = true)
	PaymentDAO paymentDAO;

	@Autowired(required = true)
	Billing billing;
	
	@Autowired(required = true)
	HttpSession session;
	
	@RequestMapping(value = "/myCart", method = RequestMethod.GET)
	public String myCart(Model model, Principal principal) 
	{
		log.debug("My Cart called.");
		try 
		{
			model.addAttribute("cart", new Cart());
			model.addAttribute("cartList", this.cartDAO.userCartList(principal.getName()));
			model.addAttribute("category", new Category());
			model.addAttribute("categoryList", this.categoryDAO.listCategory());
			model.addAttribute("displayCart", "true");

			int i;
			int sum = 0;
			int n = cartDAO.userCartList(principal.getName()).size();
			log.info("Size of cart "+n);
			for (i = 0; i < n; i++) 
			{
				sum = sum + cartDAO.userCartList(principal.getName()).get(i).getPrice();
			}
			log.info("Sum of Cart "+sum);
			model.addAttribute("sum", sum);
		} 
		
		catch (Exception ex) 
		{
			log.info("Error");
			model.addAttribute("errorMsg", "Your cart list seems to be empty. Add some items to your cart.");
		}
		return "cart-new";
	}

	@RequestMapping(value = "cart_add-{id}", method = RequestMethod.GET)
	public String addToCart(@PathVariable("id") String id, HttpServletRequest request, Principal principal,Model model, HttpSession session) 
	{
		log.info("Cart add function called.");
		try 
		{

			Product product = productDAO.getProduct(id);
			Cart cart = new Cart();
			cart.setPrice(product.getPrice());
			cart.setProduct_id(product.getId());
			cart.setProduct_name(product.getName());
			cart.setQuantity(1);
			cart.setMail_id(principal.getName());
			cart.setStatus("Added to Cart");

			cartDAO.addCart(cart);
			
			int stock = product.getStock()-1;
			product.setStock(stock);
			productDAO.addProduct(product);
		
			int n=0;
			try {
				n = cartDAO.userCartList(principal.getName()).size();
			} catch (Exception e) 
			{
				log.info("Error");
			}
			session.setAttribute("cartSize",n);
		}
		catch (Exception e) 
		{
			log.info("Error");
		}
		return "redirect:/product-{id}";
	}
	
	
	@RequestMapping(value = "buy_now-{id}", method = RequestMethod.GET)
	public String buyNow(@PathVariable("id") String id, HttpServletRequest request, Principal principal,Model model, HttpSession session) 
	{
		log.info("Cart add function called.");
		try 
		{

			Product product = productDAO.getProduct(id);
			Cart cart = new Cart();
			cart.setPrice(product.getPrice());
			cart.setProduct_id(product.getId());
			cart.setProduct_name(product.getName());
			cart.setQuantity(1);
			cart.setMail_id(principal.getName());
			cart.setStatus("Added to Cart");

			cartDAO.addCart(cart);
			
			int stock = product.getStock()-1;
			product.setStock(stock);
			productDAO.addProduct(product);
		
			int n=0;
			try {
				n = cartDAO.userCartList(principal.getName()).size();
			} catch (Exception e) 
			{
				log.info("Error");
			}
			session.setAttribute("cartSize",n);
		}
		catch (Exception e) 
		{
			log.info("Error");
		}
		return "redirect:/myCart";
	}

	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	public String checkout(Model model, Principal principal) 
	{
		log.info("Checkout Called.");
		int i;
		int s = 0;
		int n = cartDAO.userCartList(principal.getName()).size();
		log.info("Size of Checkout Cart"+n);
		for (i = 0; i < n; i++) 
		{
			s = s + cartDAO.userCartList(principal.getName()).get(i).getPrice();
		}
		model.addAttribute("sum", s);
		model.addAttribute("cart", new Cart());
		model.addAttribute("cartList", this.cartDAO.userCartList(principal.getName()));

		return "checkout";
	}

	@RequestMapping("/cart_delete-{id}")
	public String deleteCart(@PathVariable("id") int id, Model model, Principal principal, HttpSession session)
	{
		log.info("Cart Delete function called.");
		cart = cartDAO.getByIdCart(id);
		product = productDAO.getProduct(cart.getProduct_id());
		
		cartDAO.deleteCart(id);
		
		int stock = product.getStock() + 1;
		product.setStock(stock);
		productDAO.addProduct(product);
		
		int n=0;
		try {
			n = cartDAO.userCartList(principal.getName()).size();
		} catch (Exception e) {
			log.info("Error");
		}
		session.setAttribute("cartSize",n);
		return "redirect:/myCart";
	}

	@RequestMapping("/payment")
	public ModelAndView getPayment(Model model)
	{
		log.info("Payment Initiated");
		ModelAndView mv = new ModelAndView("payment-new");
		mv.addObject("payment", new Payment());
			
		try
			{
			payment = paymentDAO.getPaymentInfo(session.getAttribute("user_email").toString());
			if(payment.getF_name() != null)
				{
					mv.addObject("payment", payment);
				}
			else
				{
					mv.addObject("payment", new Payment());
				}
			}
		catch(Exception e)
			{
				log.info("New USer");
			}
			return mv;
	}
	
	@RequestMapping("/billing")
	public ModelAndView getBilling(Model model)
	{
		log.info("Billing Initiated");
		ModelAndView mv = new ModelAndView("billing_page");
		mv.addObject("billing", new Billing());
		try 
		{
			billing = billingDAO.getBilling(session.getAttribute("user_email").toString());
			if(billing.getName() != null)
				{
					mv.addObject("billing", billing);
				}
			else
				{
					mv.addObject("billing", new Billing());
				}
		}
		catch (Exception e) 
		{
			log.info("New User Detected");
		}
		return mv;
	}
}