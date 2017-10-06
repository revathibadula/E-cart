package com.mycompany.eCart.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.model.Category;
import com.mycompany.eCartBackEnd.util.Util;

@Controller
public class CategoryController 
{
	private static Logger log = LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@SuppressWarnings("unused")
	@Autowired
	private Category category;
	
	@RequestMapping(value="category_add", method= RequestMethod.POST)
	public String addCategory(Model model, @Valid @ModelAttribute("category") Category category)
	{
		log.info("Category Add Initialized.");
		Util util = new Util();
		String c_id = util.removeComma(category.getId());
		category.setId(c_id);
		categoryDAO.addCategory(category);
		log.info("Category Add Success. "+c_id);
		return "redirect:/admin_OrganizeCategory";
	}
	
	@RequestMapping("/category_delete-{id}")
	public String deleteCategory(@PathVariable("id") String id, Model model) throws Exception
	{
		log.info("Category Delete Initialized."+id);
//		System.out.println("Entering Delete Category....");
		categoryDAO.deleteCategory(id);

		log.info("Category Delete Success.");
	return "redirect:/admin_OrganizeCategory";
	}
	
	@RequestMapping("category_edit-{id}")
	public String editCategory(@PathVariable("id") String id,Model model) throws Exception
	{
		log.info("Category Edit Initialized "+id);
		model.addAttribute("category", this.categoryDAO.getCategory(id));
		model.addAttribute("categoryList", categoryDAO.listCategory());
		log.info("Category Edit Success");
		return "category";
	}
}