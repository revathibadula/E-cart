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
import com.mycompany.eCartBackEnd.dao.SupplierDAO;
import com.mycompany.eCartBackEnd.model.Supplier;
import com.mycompany.eCartBackEnd.util.Util;

@Controller
public class SupplierController 
{
	Logger log = LoggerFactory.getLogger(SupplierController.class);
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	@SuppressWarnings("unused")
	@Autowired
	private Supplier supplier;

	@RequestMapping(value="supplier_add", method=RequestMethod.POST)
	public String addSupplier(Model model, @Valid @ModelAttribute("supplier") Supplier supplier)
	{
		log.info("Supplier is being added");
		Util util = new Util();
		String s_id = util.removeComma(supplier.getId());
		supplier.setId(s_id);
		supplierDAO.addSupplier(supplier);

		return "redirect:/admin_OrganizeSupplier";
	}
	
	@RequestMapping("supplier_delete-{id}")
	public String deleteSupplier(@PathVariable("id") String id, Model model) throws Exception
	{
		log.info("Delete Supplier begins");
//		System.out.println("Entering Delete Supplier");
		supplierDAO.deleteSupplier(id);
		return "redirect:/admin_OrganizeSupplier";
	}
	
	@RequestMapping("supplier_edit-{id}")
	public String editSupplier(@PathVariable("id") String id, Model model) throws Exception
	{
		log.info("Supplier is being edited.");
		model.addAttribute("supplier", this.supplierDAO.getSupplier(id));
		model.addAttribute("supplierList", supplierDAO.listSupplier());
		return "supplier";
	}
}