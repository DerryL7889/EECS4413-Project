package com.project.project.repository;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.project.project.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT id, name, description, price, type, time, shipping, shipping_time, highest_bidder FROM Products";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("type"),
                rs.getInt("time"),
                rs.getInt("shipping"),
                rs.getInt("shipping_time"),
                rs.getString("highest_bidder")
        		)
        );
    }

    
    public List<Product> searchProducts(String keyword) throws SQLException {
    	String sql = "SELECT id, name, description, price, type, time, shipping, shipping_time, highest_bidder FROM Products WHERE name LIKE '%" + keyword + "%'";
    	return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("type"),
                rs.getInt("time"),
                rs.getInt("shipping"),
                rs.getInt("shipping_time"),
                rs.getString("highest_bidder")
        		)
        );
    }
    
    public Product getProductById(Integer id) throws SQLException{
        String sql = "SELECT id, name, description, price, type, time, shipping, shipping_time, highest_bidder FROM Products WHERE id = " + id;
        try {
        	return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("price"),
                    rs.getString("type"),
                    rs.getInt("time"),
                    rs.getInt("shipping"),
                    rs.getInt("shipping_time"),
                    rs.getString("highest_bidder")
            )
        );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void updateProductPrice(int productId, int bidAmount) throws SQLException{
        String sql = "UPDATE Products SET price = ? WHERE id = ?";
        jdbcTemplate.update(sql, bidAmount, productId);
    }

	public void setHighestBidder(int productId, String username) {
		// TODO Auto-generated method stub
		String sql = "UPDATE Products SET highest_bidder = ? WHERE id = ?";
        jdbcTemplate.update(sql, username, productId);
		
	}
    
    
    		//deletes from data base when time runs out!!! let this be commented out till everything else works
    
//    @Scheduled(fixedRate = 1000)
//    public void updateProductTimes() {
//        String sql = "UPDATE Products SET time = time - 1 WHERE time > 0";
//        int rowsAffected = jdbcTemplate.update(sql);
//        System.out.println("Number of rows updated: " + rowsAffected);
//
//        String deleteSql = "DELETE FROM Products WHERE time = 0";
//        rowsAffected = jdbcTemplate.update(deleteSql);
//        System.out.println("Number of rows deleted: " + rowsAffected);
//    }
    
    
    }
    
    
    
    

    
