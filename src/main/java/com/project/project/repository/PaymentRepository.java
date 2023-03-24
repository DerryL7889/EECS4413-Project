package com.project.project.repository;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.project.model.Product;
import com.project.project.model.User;

@Repository
public class PaymentRepository {
	
	 private final JdbcTemplate jdbcTemplate;
	 @Autowired
	    public PaymentRepository(DataSource dataSource) {
	        this.jdbcTemplate = new JdbcTemplate(dataSource);
	    }
	 
	 public void saveOrder(User user, Product product, double total_amount) {
		    String sql = "INSERT INTO Orders (user_id, product_id, total_amount) " +
		                 "VALUES (?, ?, ?)";
		    try {
		        jdbcTemplate.update(sql, user.getId(), product.getId(), total_amount);
		    } catch (DataAccessException e) {
		        System.out.println(e.getMessage());
		    }
		}


}
