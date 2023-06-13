package com.dineInOrder.application.Models;

public class orderModel {
    int orderImage;
    String orderName,price,orderId;

    public orderModel(int orderImage, String orderName, String price, String orderId) {
        this.orderImage = orderImage;
        this.orderName = orderName;
        this.price = price;
        this.orderId = orderId;
    }

    public int getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(int orderImage) {
        this.orderImage = orderImage;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderNumber) {
        this.orderId = orderId;
    }
}