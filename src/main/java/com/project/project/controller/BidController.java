package com.project.project.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.AuctionMessage;
import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.repository.ProductRepository;
import com.project.project.repository.UserRepository;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpSession;

@Controller
public class BidController {
	
	@Autowired
    private ProductRepository productRepo;
	@Autowired
    private UserRepository userRepo;
	@Autowired
	private SimpMessagingTemplate smtemplate;
	
	@RequestMapping(value="/bidPage", method = RequestMethod.GET)
	public String bidPage(@RequestParam int selectedProduct, Model model, HttpSession session) throws SQLException {
		Product product = productRepo.getProductById(selectedProduct);
		model.addAttribute("product", product);
		
		try {
			User user = (User) session.getAttribute("user");
		}catch(Exception e) {
			return "redirect:/";
		}
		
		if(product.getType().equals("forward")) {
		return "forward-auction-view";
		}
		
		System.out.println(product.getType());
		return "dutch-auction-view";
		
	}
	
//	@PostMapping("/placeBid")
//	public String placeBid(@RequestParam String productId, @RequestParam String bidAmount, @ModelAttribute("userId") String userId, Model model) throws SQLException {
//	    try {
//		int productIdInt = Integer.parseInt(productId);
//	    int bidAmountInt = Integer.parseInt(bidAmount);
//		Product product = productRepo.getProductById(productIdInt);
//	    int originalBidAmount = product.getPrice();
//
//	    if (bidAmountInt > originalBidAmount) {
//	        // Update the product with the new bid amount
//	    	productRepo.updateProductPrice(productIdInt, bidAmountInt);
//	        System.out.println("your bid has been placed");
//	        //create and broadcast auction message
//	        
//	        
//	    } else {
//	    	System.out.println("Your bid amount must be greater than the current bid amount.");
//	    }
//	    model.addAttribute("userId", userId);
//	    model.addAttribute("product", product);
//	    }catch (Exception e){
//	    	System.out.println("Your bid amount must be greater than the current bid amount.");
//	    }
//	    return "forward-auction-view";
//	}

	  @RequestMapping(value="/bidPage", method = RequestMethod.POST)
	  public String placeBid (ModelMap model, @RequestParam String productId, @RequestParam String bidAmount, @RequestParam int selectedProduct, HttpSession session) {
		  try {
			  System.out.println(productId  + " " + bidAmount + " " + selectedProduct);
			  int productIdInt = Integer.parseInt(productId);
			  //int userIdInt = Integer.parseInt(userId);
			  int amount = Integer.parseInt(bidAmount);
			  
			  User user = (User) session.getAttribute("user");//userRepo.getUserById(userIdInt);
			  String name = user.getUsername();
			  Product product = productRepo.getProductById(productIdInt);
			  int originalBidAmount = product.getPrice();
			  if (amount >= originalBidAmount) {
		        // Update the product with the new bid amount
				  productRepo.updateProductPrice(productIdInt, amount);
				  System.out.println("your bid has been placed");
				  //create and broadcast auction message
				  AuctionMessage am = new AuctionMessage(name,amount);
				  System.out.println(name + " " + amount);
				  System.out.println("/updates/"+selectedProduct);
				  smtemplate.convertAndSend("/updates/"+selectedProduct, am);
			  } else {
				  System.out.println("Your bid amount must be greater than the current bid amount.");
			  }
			  model.addAttribute("username", name);
			  model.addAttribute("product", product);
		  }catch (Exception e){
			  System.out.println(e.getMessage());
		  }
		  return "redirect:/bidPage?selectedProduct="+selectedProduct;
		}
	
		
	
	
 }
	


