package com.project.project.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.project.model.Product;
import com.project.project.repository.ProductRepository;

import jakarta.annotation.PreDestroy;

@RestController
public class ProductController {
	@Autowired
    private ProductRepository productRepo;
    
    @GetMapping("/products")
    public List<Product> showProducts(Model model) throws SQLException {
        List<Product> products = productRepo.getAllProducts();
        return products;
    }
    
    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam String keyword, Model model) throws SQLException {
        List<Product> products = productRepo.searchProducts(keyword);
        return products;
    }
    
    @PreDestroy
    public void cleanup() throws SQLException {
        productRepo.close();
    }
}