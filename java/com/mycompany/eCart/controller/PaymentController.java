package com.mycompany.eCart.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.eCartBackEnd.dao.BillingDAO;
import com.mycompany.eCartBackEnd.dao.CartDAO;
import com.mycompany.eCartBackEnd.dao.PaymentDAO;
import com.mycompany.eCartBackEnd.dao.SaveCartDAO;
import com.mycompany.eCartBackEnd.model.Billing;
import com.mycompany.eCartBackEnd.model.Cart;
import com.mycompany.eCartBackEnd.model.Payment;
import com.mycompany.eCartBackEnd.model.SaveCart;

@Controller
public class PaymentController 
{
	private static final Logger log = LoggerFactory.getLogger(CartController.class);
	
	@Autowired
	private PaymentDAO paymentDAO;
	
	@Autowired
	private BillingDAO billingDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SaveCart saveCart;
	
	@Autowired
	private SaveCartDAO saveCartDAO;
	
	@Autowired
	private CartDAO cartDAO;
	
	@RequestMapping(value="add_payment", method= RequestMethod.POST)
	public String addPaymentDetails(@Valid @ModelAttribute("payment") Payment payment)
	{
		log.info("Payment Details will be added");
		String name = session.getAttribute("user_email").toString();
//		System.out.println("User - "+name);
		payment.setMail_id(name);
//		System.out.println(payment.getF_name()+" "+payment.getL_name()+payment.getSt_line1()+" "+payment.getMethod());
		paymentDAO.addPaymentInfo(payment);
		if(payment.getMethod().equals("Card"))
		{
			log.info("Pay by Card");
			return "redirect:/billing";
		}
		else
		{
			log.info("Pay by Cash");
			return "redirect-temp";
		}
	}
	
	@RequestMapping(value="add_billing", method= RequestMethod.POST)
	public String addBillingDetails(@Valid @ModelAttribute("billing") Billing billing)
	{
		log.info("Billing Details is being saved");
		String name = session.getAttribute("user_email").toString();
//		System.out.println("User - "+name);
		billing.setMail_id(name);
		billingDAO.addBilling(billing);
		log.info("Add billing status complete.");
//		System.out.println("Status - Complete");
		return "redirect-temp";
	}
	
	@RequestMapping(value="thankyou")
	public String getThanks(Model model)
	{
		log.info("ThankYou Page initiated.");
		String bill_id = billingDAO.generateID();
		model.addAttribute("bill_id", bill_id);
		try {
			saveCart.setMail_id(session.getAttribute("user_email").toString());
			String mail_id = saveCart.getMail_id();
			List<Cart> l= this.cartDAO.userCartList(saveCart.getMail_id());
			int size = l.size();
			
//			System.out.println(size);
				for(int i=0;i < size; i++)
				{
					int Id = l.get(i).getCart_id();
					saveCart.setId(Id);
					String localDate = LocalDate.now().toString();
					saveCart.setDate(localDate);
					saveCart.setMail_id(mail_id);
					saveCart.setBill_id(bill_id);
					saveCart.setProduct_id(l.get(i).getProduct_id());
					saveCart.setName(l.get(i).getProduct_name());
					saveCart.setQuantity(l.get(i).getQuantity());
					saveCart.setPrice(l.get(i).getPrice());
					saveCartDAO.addSaveCart(saveCart);
					cartDAO.deleteCart(Id);
//					System.out.println("Mail_id "+saveCart.getMail_id()+" Bill_Id "+saveCart.getBill_id()+" AG ID "+saveCart.getId());
					model.addAttribute("saveCart", saveCart);
//					System.out.println("Success Row "+i);
				}
		} catch (Exception e) {
			return "index";
		}
		session.setAttribute("cartSize",0);
		return "thanks";
	}
}