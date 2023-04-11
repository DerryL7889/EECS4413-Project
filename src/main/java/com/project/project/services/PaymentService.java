package com.project.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.project.model.CardInfo;
import com.project.project.model.Product;
import com.project.project.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository repo;
	
	private final int EXPEDITED_SHIPPING_COST = 15;
	private final int EXPEDITED_SHIPPING_TIME = 1;

	public boolean processPayment() {
		//process payment
		
		return true;
	}
	
	public boolean processPayment(CardInfo cardInfo) {
		//process payment
		if(cardInfo != null) {
			return true;
		}
		return false;
	}
	
	public Product calculateShipping(CardInfo cardInfo, Product product) {
		//process payment
		if(cardInfo != null && product != null) {
			if(cardInfo.getExpedited()) {
				product.setShipping(product.getShipping() + EXPEDITED_SHIPPING_COST);
				product.setShipping_time(EXPEDITED_SHIPPING_TIME);
			}
		}
		return product;
	}
	
}
