package com.sportyshoes.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sportyshoes.dto.ProductDto;
import com.sportyshoes.model.Category;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;
import com.sportyshoes.service.CategoryService;
import com.sportyshoes.service.ProductService;
import com.sportyshoes.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	@Autowired
	UserService userService;
	public static String uploadDir =  System.getProperty("user.dir") + "/src/main/resources/static/productImages"; 

	@GetMapping ("/admin")
    public String adminHome () {        
		return "adminHome";
	}
	
	@GetMapping ("/admin/categories")
	public String getCategory(Model model){
		model.addAttribute("categories", categoryService.getAllCategory());
	    return "categories";
	}
	
	@GetMapping ("/admin/categories/add")
	public String getCategoryAdd(Model model){
		model.addAttribute("category", new Category());
	    return "categoriesAdd";
	}
	
	@PostMapping ("/admin/categories/add")
	public String postCategoryAdd(@ModelAttribute("category") Category category){
		categoryService.addCategory(category);
	    return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int id){
	    
		categoryService.deleteCategoryById(id);
	    return "redirect:/admin/categories";
	}

	@GetMapping ("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int id, Model model){
	   
	Optional<Category> category=categoryService.getCategoryById(id);
	   if(category.isPresent ()) {
	        model.addAttribute("category", category.get());
	        return "categoriesAdd";
	   }else
	        return "error";
	}
	
	@GetMapping ("/admin/products")
	public String getProduct(Model model){
	    
		model.addAttribute("products", productService.getAllProduct());
	    return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String getProductAdd(Model model){
	    
		model.addAttribute("productDTO", new ProductDto ());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String postProductAdd(@ModelAttribute("productDTO")ProductDto productDto,
	                             @RequestParam ("productImage")MultipartFile file,
	                             @RequestParam ("imgName")String imgName) throws IOException {
	   
		Product product = new Product();
		product.setId(productDto.getId());
		product.setName (productDto.getName ());
		product.setCategory(categoryService.getCategoryById(productDto.getCategoryId()).get());
		product.setPrice(productDto.getPrice ());
		product.setSize(productDto.getSize());
		product.setDescription(productDto.getDescription());
		
		String imageUUID;
		if(!file.isEmpty()) {
		    imageUUID = file.getOriginalFilename();
		    Path fileNameAndPath = Paths.get(uploadDir, imageUUID);
		    Files.write(fileNameAndPath, file.getBytes ());
		}else {
		    imageUUID = imgName;
		}
		product.setImageName(imageUUID);
		productService.addProduct(product);
	    return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String getDeleteProduct(@PathVariable long id){
	    productService.deleteProductById(id);
	    return "redirect:/admin/products";
	}

	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable long id, Model model){
	    
		Product product=productService.getProductById(id).get();
	    ProductDto productDto=new ProductDto();
	    productDto.setId(product.getId());
	    productDto.setName (product.getName ());
	    productDto.setCategoryId ((product.getCategory().getId()));
	    productDto.setPrice(product.getPrice());
	    productDto.setSize((product.getSize()));
	    productDto.setDescription(product.getDescription());
	    productDto.setImageName (product.getImageName ());
	    model.addAttribute("categories", categoryService.getAllCategory());
	    model.addAttribute("productDTO", productDto);
	    return "productsAdd";
	}
	
	@GetMapping ("/admin/users")
	public String getUser(Model model){
	    
		model.addAttribute("users", userService.getAllUser());
	    return "users";
	}
	
	@GetMapping ("/admin/users/search")
	public String getSearchUser(){
		
		return "searchUser";
	}
	
	@GetMapping ("/admin/users/search/{id}")
	public String getSearchUserById(@PathVariable int id, Model model){
	    
		System.out.println("getSearchUserById called");
		User user = userService.getUserById(id).get();
		model.addAttribute("user", user);
		/*
		 * Optional<User> user = userService.getUserById(id); if(user.isPresent()) {
		 * model.addAttribute("user", user.get()); System.out.println(user); return
		 * "redirect:/users"; }else return "error";
		 */
		return "user";
	}
}
