package com.project.project.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.project.project.model.Product;

@Repository
public class ProductRepository {
    private Connection conn;
    
    public ProductRepository() {
    	 try {
    		 try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             String url = "jdbc:sqlite:auction.db";
             this.conn = DriverManager.getConnection(url);
         } catch (SQLException e) {
             // Handle the exception here or throw a RuntimeException
             throw new RuntimeException(e);
         }
    }
    
    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description, price, type, time FROM Products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println(id);
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String type = rs.getString("type");
                int time = rs.getInt("time");
                products.add(new Product(id, name, description, price, type, time));
            }
        }
        return products;
    }
    
    public List<Product> searchProducts(String keyword) throws SQLException {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT id, name, description,price,type,time FROM Products WHERE name LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	 int id = rs.getInt("id");
                     String name = rs.getString("name");
                     String description = rs.getString("description");
                     int price = rs.getInt("price");
                     String type = rs.getString("type");
                     int time = rs.getInt("time");
                     products.add(new Product(id, name, description, price, type, time));
                }
            }
        }
        return products;
    }
    
    public void close() throws SQLException {
        conn.close();
    }
}
