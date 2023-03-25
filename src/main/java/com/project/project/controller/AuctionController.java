package com.project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.project.model.AuctionMessage;

@Controller
public class AuctionController {
	
	@Autowired
	private SimpMessagingTemplate smtemplate;
	
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
	  
	  @RequestMapping(value="/auction", method = RequestMethod.POST)
	  public String placeBid (ModelMap model, @RequestParam String name, @RequestParam String amount, @RequestParam String channel) {
		  AuctionMessage am = new AuctionMessage(name,Integer.parseInt(amount));
		  System.out.println(name + " " + amount);
		  System.out.println("/updates/"+channel);
		  smtemplate.convertAndSend("/updates/"+channel, am);
		  return "redirect:/auction?channel="+channel;
	  }
	  

}
