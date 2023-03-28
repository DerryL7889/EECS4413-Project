package com.project.project;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

//skeleton class to create/initialize the database
public class CreateDatabase {
	public static Connection connect(String filename) {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:./" + filename;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static void createdb(Connection conn) {
		try {
			if(conn != null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createtable(Connection conn) {
		System.out.println("---creating table---");
		String resetsql = "DROP TABLE users;";
		String createsql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	userid integer PRIMARY KEY,\n"
                + "	first_name text NOT NULL,\n"
                + " last_name text NOT NULL, \n"
                + " username text UNIQUE NOT NULL, \n"
                + " password text NOT NULL, \n"
                + " address text NOT NULL, \n"
                + " postalcode text NOT NULL, \n"
                + " city text NOT NULL, \n"
                + " province text NOT NULL, \n"
                + "	country text NOT NULL, \n"
                + "	created integer NOT NULL \n"
                + ");";
		try {
			if(conn != null) {
				 Statement stmt = conn.createStatement();
				 stmt.execute(resetsql);
				 stmt.execute(createsql);
				 System.out.println("A users table has been created.");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	

	public static void inituser(Connection conn) {
		System.out.println("---intiializing user---");
		String sql = "INSERT INTO users(first_name,"
				+ "last_name, "
				+ "username, "
				+ "password, "
				+ "address, "
				+ "postalcode, "
				+ "city, "
				+ "province, "
				+ "country, "
				+ "created) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "test");
            pstmt.setString(2, "user");
            pstmt.setString(3, "testuser");
            
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String salt = "salt";
			md.update(salt.getBytes());
            pstmt.setString(4, new String(md.digest("plaintext".getBytes())));
            
            
            pstmt.setString(5, "4700 Keele St.");
            pstmt.setString(6, "M3J 1P3");
            pstmt.setString(7, "Toronto");
            pstmt.setString(8, "ON");
            pstmt.setString(9, "Canada");
            
            Date now = new Date();
            long ut1 = now.getTime() / 1000L;
            System.out.println("user init at: " + ut1);
            
            pstmt.setLong(10, ut1);
            pstmt.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void testselect(Connection conn){
		System.out.println("---testing select user---");
        String sql = "SELECT * FROM users Where username = ?";
        
        try {
        	PreparedStatement pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1,"testuser");
        	ResultSet rs  = pstmt.executeQuery();
        	while(rs.next()) {
        		System.out.println(rs.getInt("userid") +  "\t" + 
                        rs.getString("first_name") + "\t" +
                        rs.getString("last_name") + "\t" +
                        rs.getString("username") + "\t" +
                        rs.getString("password") + "\t" +
                        rs.getString("address") + "\t" +
                        rs.getString("postalcode") + "\t" +
                        rs.getString("city") + "\t" +
                        rs.getString("country") + "\t" +
                        new Date(rs.getLong("created")).toString());
        	}
         
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void main(String[] args) {
        Connection conn = connect("auction.db");
        createdb(conn);
        createtable(conn);
        inituser(conn);
        testselect(conn);
        
    }
}
