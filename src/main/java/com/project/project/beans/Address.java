package com.project.project.beans;

public class Address {
    private String address;
    private String postalcode;
    private String city;
    private String province;
    private String country;
    
    public Address(String address, String postalcode, String city, String province, String country){
    	this.setAddress(address);
    	this.setPostalcode(postalcode);
    	this.setCity(city);
    	this.setProvince(province);
    	this.setCountry(country);
    }
    
    public Address() {
    	this.setAddress(null);
    	this.setPostalcode(null);
    	this.setCity(null);
    	this.setProvince(null);
    	this.setCountry(null);
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString() {
		return this.address +" "+ this.postalcode +" "+ this.city +", "+ this.province +", "+ this.country;
	}
}
