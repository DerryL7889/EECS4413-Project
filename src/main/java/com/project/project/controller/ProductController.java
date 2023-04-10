package com.project.project.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.project.model.Product;
import com.project.project.repository.ProductRepository;

import jakarta.annotation.PreDestroy;

@Controller
public class ProductController {
	@Autowired
    private ProductRepository productRepo;
    
    @GetMapping("/products")
    public String showProducts(@RequestParam String username, Model model) throws SQLException {
    	
        List<Product> products = productRepo.getAllProducts();
        //update time in bean to time remaining
        products = calculateTimeRemaining(products);
        model.addAttribute("products", products);
        model.addAttribute("username", username);
        return "products-view";
    }
    
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String username,@RequestParam String keyword, Model model) throws SQLException {
        List<Product> products = productRepo.searchProducts(keyword);
        products = calculateTimeRemaining(products);
        model.addAttribute("products", products);
        model.addAttribute("username",username);
        return "products-view";
    }
    
    private List<Product> calculateTimeRemaining(List<Product> products){
    	Date now = new Date();
        long ut1 = now.getTime() / 1000L;
        System.out.println(ut1);
    	for(Product p : products) {
//    		if(p.getType().equals("forward")) {
    			int remtime = p.getTime() - (int) ut1;
    			p.setTime(remtime);
//    		}else {
//    			p.setTime(0);
//    		}
        }
		return products;
    	
    }
}

