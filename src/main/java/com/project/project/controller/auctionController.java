package com.project.project.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.Product;
import com.project.project.repository.ProductRepository;

import jakarta.annotation.PreDestroy;

@Controller
public class auctionController {
	
	@Autowired
    private ProductRepository productRepo;
	
	@GetMapping("*/bidPage")
    
	public String bidPage(@RequestParam int selectedProduct, Model model) throws SQLException {
		Product product = productRepo.getProductById(selectedProduct);
        System.out.println(product.getName());
		model.addAttribute("product", product);
		
		if(product.getType().equals("forward")) {
		return "forward-auction-view";
		}
		
		System.out.println(product.getType());
		return "dutch-auction-view";
		
		
		
		
	}
	
	@PostMapping("/placeBid")
	public String placeBid(@RequestParam int productId, @RequestParam int bidAmount, Model model) throws SQLException {
	    Product product = productRepo.getProductById(productId);
	    int originalBidAmount = product.getPrice();

	    if (bidAmount > originalBidAmount) {
	        // Update the product with the new bid amount
	    	productRepo.updateProductPrice(productId, bidAmount);
	        System.out.println("your bid has been placed");
	    } else {
	    	System.out.println("Your bid amount must be greater than the current bid amount.");
	    }

	    // Return the same view with the updated message
	    model.addAttribute("product", product);
	    return "forward-auction-view";
	}

	
	
		
	
	
 }
	


