package com.project.project.services;

import java.security.MessageDigest;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import com.project.project.model.User;
import com.project.project.model.UserMapper;
import com.project.project.repository.UserRepository;

@Service
public class LoginService {
	
	@Autowired
	private UserRepository repo;
	
	public LoginService() {
		
	}

    public User validateUser(String userid, String password) {
    	
    	//hash the password here, and query for a 2 field match
    	try {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
        String salt = "salt";
		md.update(salt.getBytes());
		//salt and hash the password
		String passhash = new String(md.digest(password.getBytes()));
		return repo.validateUser(userid, passhash);
		
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    	}
    	return null;
    }
    
    public boolean userExists(String username) {
    	if(repo.findByUsername(username) != null) {
    		return true;
    	}
    	return false;
    }
    
    public User addUser(User user) {
    	User saved = repo.saveUser(user);
    	return saved;
    }

}
