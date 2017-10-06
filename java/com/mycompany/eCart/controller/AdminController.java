package com.mycompany.eCart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.dao.ProductDAO;
import com.mycompany.eCartBackEnd.dao.SupplierDAO;
import com.mycompany.eCartBackEnd.model.Category;
import com.mycompany.eCartBackEnd.model.Product;
import com.mycompany.eCartBackEnd.model.Supplier;

@Controller
public class AdminController 
{
	private static Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private Supplier supplier;

	@Autowired
	private Category category;

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private SupplierDAO supplierDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@RequestMapping("/admin_OrganizeCategory")
	public ModelAndView category(Model model) 
	{
		log.info("Category Page For Admin Opening");

		model.addAttribute("category", category);

		ModelAndView mv = new ModelAndView("category");
		mv.addObject("categoryList", categoryDAO.listCategory());
		return mv;
	}

	@RequestMapping("admin_OrganizeSupplier")
	public ModelAndView supplier(Model model) 
	{
		log.info("Supplier Page For Admin opening.");

		model.addAttribute("supplier", supplier);

		ModelAndView mv = new ModelAndView("supplier");
		mv.addObject("supplierList", supplierDAO.listSupplier());
		return mv;
	}

	@RequestMapping("admin_OrganizeProduct")
	public String product(Model model) 
	{
		log.info("Product Page For Admin opening.");
		model.addAttribute("product", new Product());
		model.addAttribute("productList", this.productDAO.listProduct());

		model.addAttribute("supplier", new Supplier());
		model.addAttribute("supplierList", this.supplierDAO.listSupplier());

		model.addAttribute("category", new Category());
		model.addAttribute("categoryList", this.categoryDAO.listCategory());

		return "product";
	}
}