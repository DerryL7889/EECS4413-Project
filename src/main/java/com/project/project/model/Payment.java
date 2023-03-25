package com.project.project.model;

public class Payment {
    private int orderId;
    private int productId;
    private int userId;
    private double amountPaid;

    public Payment(int orderId, int productId, int userId, double amountPaid) {
        this.orderId = orderId;
        this.productId = productId;
        this.userId = userId;
        this.amountPaid = amountPaid;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
