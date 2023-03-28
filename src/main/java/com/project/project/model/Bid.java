package com.project.project.model;

public class Bid {
    private int bidId;
    private int productId;
    private String username;
    private int bidAmount;

    public Bid(int productId, String username, int bidAmount) {
        this.productId = productId;
        this.username = username;
        this.bidAmount = bidAmount;
    }

    public int getBidId() {
        return bidId;
    }

    public void setBidId(int bidId) {
        this.bidId = bidId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(int bidAmount) {
        this.bidAmount = bidAmount;
    }
}
