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

import com.project.project.model.Product;
import com.project.project.model.User;
import com.project.project.repository.ProductRepository;
import com.project.project.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class PaymentController {
	
	@Autowired
    private ProductRepository productRepo;
	
	@Autowired
    private UserRepository userRepo;

	@GetMapping("/payment")
	public String showPaymentPage(@RequestParam Integer productId, Model model, HttpSession session) throws SQLException {
	    Product product = productRepo.getProductById(productId);
	    double productPrice = product.getPrice();
	    double totalAmount = productPrice + product.getShipping();
	    User user = (User) session.getAttribute("user");
	    session.setAttribute("product", product);
	    session.setAttribute("productPrice", productPrice);
	    session.setAttribute("totalAmount", totalAmount);
	    model.addAttribute("productPrice", productPrice);
	    model.addAttribute("totalAmount", totalAmount);
	    model.addAttribute("product", product);
	    model.addAttribute("user", user);
	    System.out.println(user.getFirstname());
	    return "payment-view";
	}
	
	@PostMapping("/process-payment")
	public String processPayment(ModelMap model, @RequestParam String creditCardNumber, @RequestParam String nameOnCard, @RequestParam String expirationDate, @RequestParam String securityCode, HttpSession session) {
	    // Process payment here

	    // Set attributes for receipt page
	    model.addAttribute("product", (Product)session.getAttribute("product"));
	    model.addAttribute("productPrice", session.getAttribute("productPrice"));
	    model.addAttribute("totalAmount", session.getAttribute("totalAmount"));
	    model.addAttribute("user", session.getAttribute("user"));

	    return "receipt-view";
	}
}

