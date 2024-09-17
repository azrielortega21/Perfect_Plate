package com.mobdeve.s14.group4;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.ArrayList;
import java.util.Date;

public class Order {
    private String orderId;
    private ArrayList<OrderDetails> orderDetails;
    private Object timestamp;

    //customer details
    private String customer;
    private Address address;

    //order status
    private boolean paid;
    private boolean delivered;

    public Order(){
        this.orderDetails = new ArrayList<OrderDetails>();
        this.paid = false;
        this.delivered = false;
    }

    public Order(String customer, Address address){
        this.customer = customer;
        this.address = address;
        this.orderDetails = new ArrayList<OrderDetails>();
        this.timestamp = ServerValue.TIMESTAMP;
        this.paid = false;
        this.delivered = false;
    }

    //
    // Getters and Setters
    //
    public String getId() {
        return orderId;
    }

    public ArrayList<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public String getCustomer() {
        return customer;
    }

    public Address getAddress() {
        return address;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public Date getDate(){
        Date date;
        try {
            date = new Date((long) timestamp);
        }
        catch (Exception e){
            date = null;
        }
        return date;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderDetails(ArrayList<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    //
    // Functions
    //
    public void addOrderDetail(OrderDetails od){
        OrderDetails temp = null;

        for (OrderDetails od1 : orderDetails){
            if (od1.getBook() == od.getBook()){
                temp = od1;
                break;
            }
        }

        if (temp == null)
            orderDetails.add(od);
        else
            temp.addOne();
    }

    public void removeOrderDetail(OrderDetails od) { orderDetails.remove(od); }

    public void removeOrderDetail(int position) { orderDetails.remove(position); }

    @Exclude
    public double getTotal(){
        double quantity = 0;

        for (OrderDetails od : orderDetails){
            quantity += (od.getBook().getPrice() * od.getQuantity());
        }

        return quantity;
    }
}