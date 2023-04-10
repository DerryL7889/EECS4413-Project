package com.project.project.controller;

import java.sql.SQLException;
import java.util.Date;
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
import com.project.project.model.Bid;
import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.repository.BidRepository;
import com.project.project.repository.ProductRepository;
import com.project.project.repository.UserRepository;
import com.project.project.services.BidService;

import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class BidController {
	
	@Autowired
    private ProductRepository productRepo;
	@Autowired
    private UserRepository userRepo;
	@Autowired
	private SimpMessagingTemplate smtemplate;
	
	@Autowired
	private BidRepository bidRepo;
	
	@Autowired
	private BidService service;
	
	@RequestMapping(value="/bidPage", method = RequestMethod.GET)
	public String bidPage(@RequestParam("username") String username, @RequestParam Integer selectedProduct, Model model, HttpSession session) throws SQLException {
		Product product = productRepo.getProductById(selectedProduct);
		model.addAttribute("product", product);
		//model.addAttribute("username",username);
		String name = bidRepo.getHighestBidderbyProductId(selectedProduct);
		User user = userRepo.findByUsername(username);
		model.addAttribute("name", name);
		model.addAttribute("user", user);
		model.addAttribute("username",username);
		if(product.getType().equals("forward")) {
			
			Date now = new Date();
	        long ut1 = now.getTime() / 1000L;
	        System.out.println(ut1);
			int remtime = product.getTime() - (int) ut1;
			model.addAttribute("remTime", remtime);
			
			if(remtime < 0) {
				if(product.getHighestBidder()!=null && product.getHighestBidder().equals(user.getUsername())) {
					return "forward-auction-win-view";
				}else {
					return "redirect:/products?username="+user.getUsername();
				}
			}else {
				return "forward-auction-view";
			}
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

	  @RequestMapping(value="/placeBid", method = RequestMethod.POST)
	  public String placeBid (ModelMap model,@RequestParam String username, @RequestParam String productId, @RequestParam String bidAmount, @RequestParam int selectedProduct, HttpSession session) {
		  try {
			  System.out.println(productId  + " " + bidAmount + " " + selectedProduct);
			  int productIdInt = Integer.parseInt(productId);
			  //int userIdInt = Integer.parseInt(userId);
			  int amount = Integer.parseInt(bidAmount);
			  
			  //call the bidservice to place a bid
			  AuctionMessage am = service.placeBid(productIdInt, username, amount);
			  if(am != null) {
				  System.out.println(username + " " + amount);
				  System.out.println("/updates/"+selectedProduct);
				  smtemplate.convertAndSend("/updates/"+selectedProduct, am);
			  }else {
				  System.out.println("Your bid amount must be greater than the current bid amount, or the auction has ended");
			  }
			 
			  String name = username;
			  Product product = productRepo.getProductById(productIdInt);
//			  int originalBidAmount = product.getPrice();
//			  
//			  Date now = new Date();
//		        long ut1 = now.getTime() / 1000L;
//		        System.out.println(ut1);
//				int remtime = product.getTime() - (int) ut1;
//			  
//			  
//			  if (amount > originalBidAmount && remtime > 0) {
//		        // Update the product with the new bid amount
//				  productRepo.updateProductPrice(productIdInt, amount);
//				  productRepo.setHighestBidder(productIdInt, username);
//				  System.out.println("your bid has been placed");
//				  //create and broadcast auction message
//				  AuctionMessage am = new AuctionMessage(name,amount);
//				  System.out.println(name + " " + amount);
//				  System.out.println("/updates/"+selectedProduct);
//				  smtemplate.convertAndSend("/updates/"+selectedProduct, am);
//			  } else {
//				  System.out.println("Your bid amount must be greater than the current bid amount, or the auction has ended");
//			  }
			  model.addAttribute("username", name);
			  model.addAttribute("product", product);
		  }catch (Exception e){
			  System.out.println(e.getMessage());
		  }
		  
		  return "redirect:/bidPage?username=" + username + "&selectedProduct="+selectedProduct;
		}
	
		
	
	
 }