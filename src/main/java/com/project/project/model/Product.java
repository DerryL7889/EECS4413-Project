package com.project.project.model;


public class Product {
    private Integer id;
    private String name;
    private String description;
    private Integer price;
    private String type;
    private Integer time;
    private Integer shipping;
	private Integer shipping_time;
	public String bidder;         	   							   		
    
    public Product(int id, String name, String description, int price, String type, int time, int shipping, int shipping_time, String bidder) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.type = type;
		this.time = time;
		this.shipping = shipping;
		this.setShipping_time(shipping_time);
		this.setBidder(bidder);
		
	}
    
    public String getBidder() {
    	return bidder;
    }
    

    public void setBidder(String bidder) {
    	this.bidder = bidder;
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

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public Integer getShipping_time() {
		return shipping_time;
	}

	public void setShipping_time(Integer shipping_time) {
		this.shipping_time = shipping_time;
	}
}