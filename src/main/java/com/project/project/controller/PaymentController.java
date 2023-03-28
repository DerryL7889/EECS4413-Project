package com.project.project.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.project.model.Payment;
import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.repository.PaymentRepository;
import com.project.project.repository.ProductRepository;
import com.project.project.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
    private ProductRepository productRepo;
	
	@Autowired
    private PaymentRepository paymentRepo;
	
	@Autowired
    private UserRepository userRepo;

	@GetMapping("/payment")
	public String showPaymentPage(@RequestParam String username, @RequestParam Integer selectedProduct, Model model) throws SQLException {
		 Product product = productRepo.getProductById(selectedProduct);
		    double productPrice = product.getPrice();
		    double totalAmount = productPrice + product.getShipping();
		    User user = userRepo.findByUsername(username);
		    System.out.println("In showPaymentPage" + user.getFirstname());
		    model.addAttribute("productPrice", productPrice);
		    model.addAttribute("totalAmount", totalAmount);
		    model.addAttribute("selectedProduct", selectedProduct);
		    model.addAttribute("user", user);
		    model.addAttribute("product", product);
		    return "payment-view";
	}
	
	@GetMapping("/process-payment")
	public String processPayment(ModelMap model,@RequestParam String username, @RequestParam("productId") Integer productId,HttpSession session) throws SQLException {
		Product product = productRepo.getProductById(productId);
		double productPrice = product.getPrice();
	    double totalAmount = productPrice + product.getShipping();
		User user = userRepo.findByUsername(username);
		System.out.println(user.getFirstname());
		paymentRepo.saveOrder(user, product, totalAmount);
	    model.addAttribute("product", product);
	    model.addAttribute("productPrice", productPrice);
	    model.addAttribute("totalAmount", totalAmount);
	    model.addAttribute("user", user);
	    return "receipt-view";
	}
}

