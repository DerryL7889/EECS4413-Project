package com.project.project.beans;

import java.sql.ResultSet;
import java.sql.SQLException;
  
import org.springframework.jdbc.core.RowMapper;
  
public class UserMapper implements RowMapper<User>{
  
    @Override
    public User mapRow(ResultSet rs, int map) throws SQLException {
  
        User user = new User();
        Address addr = new Address();
        user.setId(rs.getInt("userid"));
        user.setFirstname(rs.getString("first_name"));
        user.setLastname(rs.getString("last_name"));
        user.setUsername(rs.getString("username"));
        user.setPasshash(rs.getString("password"));
        user.setCreated(rs.getInt("created"));
        
        addr.setAddress(rs.getString("address"));
        addr.setPostalcode(rs.getString("postalcode"));
        addr.setCity(rs.getString("city"));
        addr.setProvince(rs.getString("province"));
        addr.setCountry(rs.getString("country"));
        
        user.setAddress(addr);
        return user;
    }
  
}
