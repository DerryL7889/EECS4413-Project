package com.project.project.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.project.model.AuctionMessage;
import com.project.project.model.Product;
import com.project.project.repository.ProductRepository;

@Controller
public class AuctionController {
	
	@Autowired
	private SimpMessagingTemplate smtemplate;
	@Autowired
	private ProductRepository productRepo;
	
	  @RequestMapping(value="/button", method = RequestMethod.GET)
	  public String showButtonPage(ModelMap model){
		  return "buttonpage";
	  }
	  
	  @RequestMapping(value="/button", method = RequestMethod.POST)
	  public String onButtonClick(ModelMap model){
			  System.out.println("clicky");
			  smtemplate.convertAndSend("/updates/testchannel", new AuctionMessage("bidder",15));
	    return "buttonpage";
	  }
	  
	  @RequestMapping(value="/auction", method = RequestMethod.GET)
	  public String showAuctionPage(ModelMap model, @RequestParam String channel) {
		  model.addAttribute("channel", channel);
		  return "auction";
	  }
	  
//	  @RequestMapping(value="/auction", method = RequestMethod.POST)
//	  public String placeBid (ModelMap model, @RequestParam String name, @RequestParam String amount, @RequestParam String channel) {
//		  AuctionMessage am = new AuctionMessage(name,Integer.parseInt(amount));
//		  System.out.println(name + " " + amount);
//		  System.out.println("/updates/"+channel);
//		  smtemplate.convertAndSend("/updates/"+channel, am);
//		  return "redirect:/auction?channel="+channel;
//	  }
	  
	  @RequestMapping(value="/reset", method = RequestMethod.GET)
	  public String showreset (ModelMap model) {
		  return "buttonpage";
	  }
	  
	  @RequestMapping(value="/reset", method = RequestMethod.POST)
	  public String doreset (ModelMap model) {
		  Date now = new Date();
          long ut1 = now.getTime() / 1000L;
          System.out.println(ut1);
          List<Product> products = productRepo.getAllProducts();
          for (Product p: products) {
        	  productRepo.setEndTime(p.getId(), (int) (ut1 + 10));
          }
          return "buttonpage";
	  }
	  

}
