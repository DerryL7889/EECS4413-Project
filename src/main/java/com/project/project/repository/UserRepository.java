package com.project.project.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;

import com.project.project.model.UserMapper;
import com.project.project.model.Address;
import com.project.project.model.Product;
import com.project.project.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

@Repository
public class UserRepository {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
    public UserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	
//    public User validateUser(String userid, String password) {
//    	String sql = "SELECT * FROM users Where username = ?";
//    	try {
//    	List<User> results = jdbcTemplate.query(
//                sql, new PreparedStatementSetter() {
//                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
//                       preparedStatement.setString(1, userid);
//                    }
//                 },
//                 new UserMapper());
//    	if(results.size() > 0) {
//    		User user = results.get(0);
//    		
//    		MessageDigest md = MessageDigest.getInstance("SHA-512");
//            String salt = "salt";
//			md.update(salt.getBytes());
//			//salt and hash the password
//    		String passhash = new String(md.digest(password.getBytes()));
//    		
//    		if(passhash.equals(user.getPasshash())) {
//    			//clear password field of bean before returning it for security
//    			user.setPasshash("[this-should-be-blank]");
//    			return user;
//    		}
//    	}
//    	}catch(Exception e) {
//    		System.out.println(e.getMessage());
//    	}
//    	return null;
//    }
    
    public User validateUser(String userid, String passhash) {
    	String sql = "SELECT * FROM users Where username = ? AND password = ?";
    	try {
	    	List<User> results = jdbcTemplate.query(
	                sql, new PreparedStatementSetter() {
	                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
	                       preparedStatement.setString(1, userid);
	                       preparedStatement.setString(2, passhash);
	                    }
	                 },
	                 new UserMapper());
	    	if(results.size() > 0) {
	    		User user = results.get(0);
	    		user.setPasshash("[this-should-be-blank]");
	    		return user;
	    	}else {
	    		return null;
	    	}
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return null;
    }

    public User saveUser(User user) {
    	String validateSql = "SELECT * FROM users Where username = ?";
        String saveSql = "INSERT INTO Users (first_name, last_name, username, password, address, postalcode, city, province, country, created) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
        	if(this.findByUsername(user.getUsername()) != null) {
        		System.out.println("Ducplicate user not created");
        		return null;
        	}else {
        		//hash the user password for security
        		String password = user.getPasshash();
        		MessageDigest md = MessageDigest.getInstance("SHA-512");
                String salt = "salt";
    			md.update(salt.getBytes());
    			//salt and hash the password
        		String passhash = new String(md.digest(password.getBytes()));
        		user.setPasshash(passhash);
        		
        		//save the user
	            jdbcTemplate.update(saveSql,
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
	            
	            return user;
        	}
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }

    
//    public void saveUser(User user) {
//        String sql = "INSERT INTO Users (first_name, last_name, username, password, address, postalcode, city, province, country, created) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        try {
//            jdbcTemplate.update(sql,
//                    user.getFirstname(),
//                    user.getLastname(),
//                    user.getUsername(),
//                    user.getPasshash(),
//                    user.getAddress().getAddress(),
//                    user.getAddress().getPostalcode(),
//                    user.getAddress().getCity(),
//                    user.getAddress().getProvince(),
//                    user.getAddress().getCountry(),
//                    user.getCreated());
//        } catch (DataAccessException e) {
//            System.out.println(e.getMessage());
//        }
//    }

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
