package com.project.project.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.project.model.Bid;
import com.project.project.model.Product;
import com.project.project.model.User;

@Repository
public class BidRepository {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
    public BidRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	 public void saveBid(Bid bid) {
		    String sql = "INSERT INTO Bids (Product_id, Username, Bid_amount) " +
		                 "VALUES (?, ?, ?)";
		    try {
		        jdbcTemplate.update(sql, bid.getProductId(), bid.getUsername(), bid.getBidAmount());
		    } catch (DataAccessException e) {
		        System.out.println(e.getMessage());
		    }
		}
	 
	 public String getHighestBidderbyProductId(Integer productId) {
		    String highestBidder = null;
		    int highestBidAmount = 0;
		    String sql = "SELECT * FROM Bids WHERE Product_id = ?";
		    try {
		        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, productId);
		        for (Map<String, Object> row : rows) {
		            int bidAmount = (int) row.get("Bid_amount");
		            if (bidAmount > highestBidAmount) {
		                highestBidAmount = bidAmount;
		                highestBidder = (String) row.get("Username");
		            }
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return highestBidder;
		}

}
