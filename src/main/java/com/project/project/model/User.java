package com.project.project.model;


public class User  {
	private int id;
	private String firstname;
    private String lastname;
    private String username;
    private String passhash;
    private Address address;
    private int created;
    
    public User(int id, String firstname, String lastname, String username, String passhash, Address address, int created) {
    	this.setId(id);
    	this.setFirstname(firstname);
    	this.setLastname(lastname);
    	this.setUsername(username);
    	this.setPasshash(passhash);
    	this.setAddress(address);
    	this.setCreated(created);
    }

	public User() {
		this.firstname = null;
		this.lastname = null;
		this.username = null;
		this.passhash = null;
		this.address = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPasshash() {
		return passhash;
	}

	public void setPasshash(String passhash) {
		this.passhash = passhash;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}
    
	public String toString() {
		return this.id +" "+ this.firstname +" "+ this.lastname +" "+ this.username +" "+ this.passhash +"\n"+
				this.address.toString()+"\n"+
				"created at: " + this.created;
	}
}
