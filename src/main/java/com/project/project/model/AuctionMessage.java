package com.project.project.model;

public class AuctionMessage {
	private String Bidder;
	private double amount;
	
	public AuctionMessage(String bidder, double amount) {
		this.setBidder(bidder);
		this.setAmount(amount);
	}
	
	public String getBidder() {
		return Bidder;
	}
	public void setBidder(String bidder) {
		Bidder = bidder;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
