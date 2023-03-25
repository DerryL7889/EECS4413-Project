package com.project.project.model;

public class AuctionMessage {
	private String Bidder;
	private int amount;
	
	public AuctionMessage(String bidder, int amount) {
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
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
