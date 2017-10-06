package com.mycompany.eCart.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.eCartBackEnd.dao.CategoryDAO;
import com.mycompany.eCartBackEnd.dao.ProductDAO;
import com.mycompany.eCartBackEnd.dao.SupplierDAO;
import com.mycompany.eCartBackEnd.model.Category;
import com.mycompany.eCartBackEnd.model.Product;
import com.mycompany.eCartBackEnd.model.Supplier;
import com.mycompany.eCartBackEnd.util.Util;

@Controller
public class ProductController 
{
	private static Logger log = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductDAO productDAO;

	@Autowired
	private CategoryDAO categoryDAO;

	@Autowired
	private SupplierDAO supplierDAO;

	private Path path;
	
	@RequestMapping(value="product_add", method= RequestMethod.POST)
	public String addProduct(Model model, @Valid @ModelAttribute("product") Product product, HttpServletRequest request)
	{
		log.info("Product is being added");
//		System.out.println(product.getCategory().getName());
		Category category= categoryDAO.getByName(product.getCategory().getName());
//		System.out.println(category.getName());
		
		Supplier supplier = supplierDAO.getByName(product.getSupplier().getName());
		
		product.setCategory(category);
		product.setSupplier(supplier);
		
		product.setCategory_id(category.getId());
		product.setSupplier_id(supplier.getId());

		Util util = new Util();
		String p_id = util.removeComma(product.getId());
		product.setId(p_id);
		productDAO.addProduct(product);

		MultipartFile file = product.getImage();
//		System.out.println(product.getImage());
		String rootDirectory=request.getSession().getServletContext().getRealPath("/");
		path =Paths.get(rootDirectory + "\\WEB-INF\\resources\\images\\"+product.getId()+".jpg");
		if(file!=null && !file.isEmpty())
		{
			try{
				file.transferTo(new File(path.toString()));
				log.info("image uploaded");
//				System.out.println("image uploaded....");
			}
		catch(Exception e)
		{
//		e.printStackTrace();
		throw new RuntimeException("image saving failed",e);
		}
		}
		log.info("Product added successfully");
			return "redirect:/admin_OrganizeProduct";
	}

	@RequestMapping("/product_delete-{id}")
	public String deleteProduct(@PathVariable("id") String id, Model model) throws Exception 
	{
		log.info("Product Delete Initialized " + id);
		productDAO.deleteProduct(id);
		log.info("Product Delete Success");
		return "redirect:/admin_OrganizeProduct";
	}

	@RequestMapping("product_edit-{id}")
	public String editCategory(@PathVariable("id") String id, Model model) throws Exception 
	{
		log.info("Product Edit Initialized " + id);
		model.addAttribute("product", this.productDAO.getProduct(id));
		model.addAttribute("categoryList", this.categoryDAO.listCategory());
		model.addAttribute("productList", this.productDAO.listProduct());
		model.addAttribute("supplierList", this.supplierDAO.listSupplier());
		log.info("Product Edit Success");
		return "product";
	}
	
	@RequestMapping(value="/product-{id}")
	public String viewProduct(@PathVariable("id") String id, Model model)
	{
		log.info("Get Product initiated");
		model.addAttribute("selectedProduct", this.productDAO.getProduct(id));
		return "item";
	}
	
}