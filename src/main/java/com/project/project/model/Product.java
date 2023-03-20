package com.project.project.model;


public class Product {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String type;
    private Integer time;
    
    public Product(int id, String name, String description, int price, String type, int time) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
		this.time = time;
		
	}

	public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setCurrentBidPrice(Integer price) {
        this.price = price;
    }

    public String getAuctionType() {
        return type;
    }

    public void setAuctionType(String type) {
        this.type = type;
    }

    public Integer getRemainingTime() {
        return time;
    }

    public void setRemainingTime(Integer time) {
        this.time = time;
    }
}