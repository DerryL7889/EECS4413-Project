package com.project.project.data;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;

import com.project.project.beans.User;
import com.project.project.beans.UserMapper;

@Repository
public class LoginService {
	
	private final JdbcTemplate jdbcTemplate;

	@Autowired
    public LoginService(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	
    public User validateUser(String userid, String password) {
    	String sql = "SELECT * FROM users Where username = ?";
    	try {
    	List<User> results = jdbcTemplate.query(
                sql, new PreparedStatementSetter() {
                    public void setValues(PreparedStatement preparedStatement) throws SQLException {
                       preparedStatement.setString(1, userid);
                    }
                 },
                 new UserMapper());
    	if(results.size() > 0) {
    		User user = results.get(0);
    		
    		MessageDigest md = MessageDigest.getInstance("SHA-512");
            String salt = "salt";
			md.update(salt.getBytes());
			//salt and hash the password
    		String passhash = new String(md.digest(password.getBytes()));
    		
    		if(passhash.equals(user.getPasshash())) {
    			//clear password field of bean before returning it for security
    			user.setPasshash("[this-should-be-blank]");
    			return user;
    		}
    	}
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return null;
    }
}
