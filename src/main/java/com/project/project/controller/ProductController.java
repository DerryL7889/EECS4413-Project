package com.project.project.controller;

import java.sql.SQLException;
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
    public String showProducts(Model model) throws SQLException {
        List<Product> products = productRepo.getAllProducts();
        System.out.println(products);
        model.addAttribute("products", products);
        return "products-view";
    }
    
    @GetMapping("/products/search")
    public String searchProducts(@RequestParam String keyword, Model model) throws SQLException {
        List<Product> products = productRepo.searchProducts(keyword);
        model.addAttribute("products", products);
        return "products-view";
    }
}

