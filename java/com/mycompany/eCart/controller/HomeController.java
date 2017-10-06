package com.mycompany.eCart.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.eCartBackEnd.dao.CartDAO;
import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.dao.UserDAO;
import com.mycompany.eCartBackEnd.model.Category;
import com.mycompany.eCartBackEnd.model.Product;
import com.mycompany.eCartBackEnd.model.Supplier;
import com.mycompany.eCartBackEnd.model.User;

@Controller
public class HomeController 
{
		private static Logger log = LoggerFactory.getLogger(HomeController.class);
		
		@Autowired
		private CategoryDAO categoryDAO;
		
		@Autowired
		private Category category;
		
		@Autowired
		private Supplier supplier;
		
		@Autowired
		private Product product;
		
		@Autowired
		private User user;
		
		@Autowired
		private UserDAO userDAO;
		
		@Autowired
		private CartDAO cartDAO;
		
		@RequestMapping("/")
		public ModelAndView landingPage(HttpSession session, Principal principal)
		{
			log.info("Home Page opening.");
			ModelAndView mv = new ModelAndView("index");
			session.setAttribute("category", category);
			session.setAttribute("product", product);
			session.setAttribute("supplier", supplier);
			session.setAttribute("categoryList", categoryDAO.listCategory());
			
			int n=0;
			try {
				n = cartDAO.userCartList(principal.getName()).size();
			} catch (Exception e) {
//				e.printStackTrace();
			}
			session.setAttribute("cartSize",n);
			
			try 
			{
				String name = principal.getName();
//				System.out.println("User - "+name);
				session.setAttribute("user_email", name);
				user = userDAO.getUser(name);
				session.setAttribute("user_f_name", user.getF_name());
			}
			catch (Exception e) 
			{
				log.info("User has not yet logged in");
//				System.out.println("Null.");
			}
			return mv;
		}
		
		
		@RequestMapping("/onLoad")
		public ModelAndView getLandPage(HttpSession session)
		{
			log.info("Home Page opening.");
			ModelAndView mv = new ModelAndView("index");
			session.setAttribute("category", category);
			session.setAttribute("product", product);
			session.setAttribute("supplier", supplier);
			
			session.setAttribute("categoryList", categoryDAO.listCategory());
			
			return mv;
		}
		
		@RequestMapping(value="/admin")
		public ModelAndView testadmin()
		{
			log.info("Admin Page opening.");
			ModelAndView mv = new ModelAndView("admin");
			return mv;
		}
		
		@RequestMapping(value="/home")
		public String homePage()
		{
			return "redirect:/";
		}
		
		@RequestMapping(value="/aboutUs")
		public String aboutUs()
		{
			log.info("AboutUs Page opening");
			return "aboutUs";
		}
		
		@RequestMapping(value="/category-{id}")
		public ModelAndView categoryPage(@PathVariable("id") String id, Model model)
		{
			log.info("Category Page Opened by User");
			ModelAndView mv = new ModelAndView("allProduct");
			mv.addObject("category", this.categoryDAO.getCategory(id));
			
			return mv;
		}
}