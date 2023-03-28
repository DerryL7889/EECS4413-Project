package com.project.project.repository;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import com.project.project.model.Address;
import com.project.project.model.Product;
import com.project.project.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class UserRepository {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	
	public User validateUser(String username, String password) {
	    String sql = "SELECT * FROM users WHERE username = '" + username + "'";
	    List<User> users = jdbcTemplate.query(sql, (rs, rowNum) ->
	        new User(
	            rs.getInt("userid"),
	            rs.getString("first_name"),
	            rs.getString("last_name"),
	            rs.getString("username"),
	            rs.getString("password"),
	            new Address(
	                    rs.getString("address"),
	                    rs.getString("postalcode"),
	                    rs.getString("city"),
	                    rs.getString("province"),
	                    rs.getString("country")
	                ),
	            rs.getInt("created")
	        )
	    );

	    if (users.size() == 1 && users.get(0).getPasshash().equals(password)) {
	        return users.get(0);
	    } else {
	        return null;
	    }
	}


    
    public void saveUser(User user) {
        String sql = "INSERT INTO Users (first_name, last_name, username, password, address, postalcode, city, province, country, created) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,
                    user.getFirstname(),
                    user.getLastname(),
                    user.getUsername(),
                    user.getPasshash(),
                    user.getAddress().getAddress(),
                    user.getAddress().getPostalcode(),
                    user.getAddress().getCity(),
                    user.getAddress().getProvince(),
                    user.getAddress().getCountry(),
                    user.getCreated());
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserById(Integer id) throws SQLException{
        String sql = "SELECT * FROM Users WHERE userid = " + id;
        try {
        	return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
        	new User(
    	            rs.getInt("userid"),
    	            rs.getString("first_name"),
    	            rs.getString("last_name"),
    	            rs.getString("username"),
    	            rs.getString("password"),
    	            new Address(
    	                    rs.getString("address"),
    	                    rs.getString("postalcode"),
    	                    rs.getString("city"),
    	                    rs.getString("province"),
    	                    rs.getString("country")
    	                ),
    	            rs.getInt("created")
    	        )
    	    );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		
		String sql = "SELECT * FROM users WHERE username = '" + username + "'";
		        try {
		        	return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
		        	new User(
		    	            rs.getInt("userid"),
		    	            rs.getString("first_name"),
		    	            rs.getString("last_name"),
		    	            rs.getString("username"),
		    	            rs.getString("password"),
		    	            new Address(
		    	                    rs.getString("address"),
		    	                    rs.getString("postalcode"),
		    	                    rs.getString("city"),
		    	                    rs.getString("province"),
		    	                    rs.getString("country")
		    	                ),
		    	            rs.getInt("created")
		    	        )
		    	    );
		        } catch (EmptyResultDataAccessException e) {
		            return null;
		        }
		    
	}
}
