package com.mycompany.eCart.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.eCartBackEnd.dao.SaveCartDAO;
import com.mycompany.eCartBackEnd.dao.UserDAO;
import com.mycompany.eCartBackEnd.model.SaveCart;
import com.mycompany.eCartBackEnd.model.User;

@Controller
public class UserController 
{
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private User user;
	
	@Autowired
	private SaveCartDAO saveCartDAO;
	
	@RequestMapping(value="/login")
	public String login(@RequestParam(value="error",required=false)String error,@RequestParam(value="logout",required=false) String logout, Model model)
	{
		if(session.getAttribute("user_f_name") != null)
		{
			return "redirect:/";
		}
		else
		{
			if(error!=null)
			{
				log.info("Error");
//				System.out.println("Error..");
				model.addAttribute("loginmsg","Invalid username or password...");
			}
		
			if(logout!=null)
			{
				log.info("Logout Called");
//				System.out.println("logout called..");
				model.addAttribute("loginmsg","you have logged out...");
			}
			return "login";
		}
	}
	
	@RequestMapping("/viewProfile")
	public String getProfile(Model model)
	{
		log.info("View User Profile Called");
		String mail_id = session.getAttribute("user_email").toString();
		user = userDAO.getUser(mail_id);
		model.addAttribute("user", user);
		List<String> billsList = saveCartDAO.getBills(mail_id);
		model.addAttribute("billsList", billsList);
		return "getProfile";
	}
	
	@RequestMapping(value="getorder-{bill}")
	public String getOrder(@PathVariable("bill") String id, Model model)
	{
		log.info("Get Order Details called "+id);
		List<SaveCart> saveCart = saveCartDAO.getSavedCart(id);
		model.addAttribute("saveCartList", saveCart);
		model.addAttribute("bill_id", id);
		model.addAttribute("date", saveCart.get(0).getDate());

		int i;
		int sum = 0;
		int n = saveCartDAO.getSavedCart(id).size();
//		System.out.println(n);
		for (i = 0; i < n; i++) 
		{
			sum = sum + saveCartDAO.getSavedCart(id).get(i).getPrice();
		}
		model.addAttribute("sum", sum);
		return "getOrder";
	}
}