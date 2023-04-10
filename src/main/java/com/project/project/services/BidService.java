package com.project.project.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.project.project.model.AuctionMessage;
import com.project.project.model.Product;
import com.project.project.repository.BidRepository;
import com.project.project.repository.ProductRepository;
import com.project.project.repository.UserRepository;

@Service
public class BidService {
	
	
	@Autowired
    private ProductRepository productRepo;
	@Autowired
    private UserRepository userRepo;
	@Autowired
	private BidRepository bidRepo;

	private List<Product> products;
	private final int DUTCH_AUCTION_INTERVAL = 60;
	private final int DUTCH_AUCTION_PRICE = 5;
	private final int DUTCH_AUCTION_MINIMUM = 10;
	
	public BidService() {
	}
	
	public AuctionMessage placeBid(int productIdInt, String name, int amount) {
		try {
		Product product = productRepo.getProductById(productIdInt);
		int originalBidAmount = product.getPrice();
		
		Date now = new Date();
        long ut1 = now.getTime() / 1000L;
        System.out.println(ut1);
		int remtime = product.getTime() - (int) ut1;
		
		if (amount > originalBidAmount && remtime > 0) {
	        // Update the product with the new bid amount
			  productRepo.updateProductPrice(productIdInt, amount);
			  productRepo.setHighestBidder(productIdInt, name);
			  System.out.println("your bid has been placed");
			  //create and broadcast auction message
			  AuctionMessage am = new AuctionMessage(name,amount);
			  return am;
		  } else {
			  return null;
		  }
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@Scheduled(fixedRate = 1000)
	public void updateProductTimes() {
		if(products == null) {
			this.syncRepo();
		}
		Date now = new Date();
		long ut1 = now.getTime() / 1000L;
		boolean changed = false;
		for(Product p: products) {
			if(p.getType().equals("dutch") && p.getTime() <= (int)ut1 && p.getPrice() > DUTCH_AUCTION_MINIMUM) {
				try {
					System.out.println(p.getName() + " | dutch auction timer expired, adjusting price");
					int newPrice = p.getPrice() - DUTCH_AUCTION_PRICE;
					System.out.println(p.getPrice() + " -> " + newPrice);
					//adjust price
					productRepo.updateProductPrice(p.getId(), newPrice);
					//adjust time
					productRepo.setEndTime(p.getId(), (int)ut1 + DUTCH_AUCTION_INTERVAL);
					
				}catch(Exception e) {
					System.out.println(e.getMessage());
				}
				changed = true;
			}
		}
		if(changed){
			syncRepo();
		}
	}
	
	private void syncRepo() {
		this.products = productRepo.getAllProducts();
	}
}
