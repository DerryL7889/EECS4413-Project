package com.project.project.repository;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import com.project.project.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT id, name, description, price, type, time FROM Products";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("type"),
                rs.getInt("time")
        		)
        );
    }

    
    public List<Product> searchProducts(String keyword) throws SQLException {
    	String sql = "SELECT id, name, description, price, type, time FROM Products WHERE name LIKE '%" + keyword + "%'";
    	return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Product(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("type"),
                rs.getInt("time")
        		)
        );
    }
}
    
    
