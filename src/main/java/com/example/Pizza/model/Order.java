package com.example.Pizza.model;

import java.io.Serializable;
import java.io.StringReader;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Order implements Serializable{
    private double totalCost = 0;
    private String orderId;
    private Pizza pizza;
    private Delivery delivery;

    public Order(Pizza p, Delivery d){
        this.pizza = p;
        this.delivery = d;
    }

    public double getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public Pizza getPizza() {
        return pizza;
    }
    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
    public Delivery getDelivery() {
        return delivery;
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public String getName() { return this.getDelivery().getName();}
    public String getPizzaName() { return this.getPizza().getPizza();}
    public String getAddress() { return this.getDelivery().getAddress();}
    public String getPhone() { return this.getDelivery().getPhone();}
    public boolean getRush() { return this.getDelivery().isRush();}
    public String getComments() { return this.getDelivery().getComments();}
    public String getSize() { return this.getPizza().getSize();}
    public int getQuantity() { return this.getPizza().getQuantity();}
    
    public double getPizzaCost(){
        return this.getRush() ? this.getTotalCost() - 2 : this.getTotalCost();
    }

    public static JsonObject toJSON(String json){
        JsonReader r = (JsonReader) Json.createReader(new StringReader(json));
        return r.readObject();
    }

    public static Order create(String jsonStr){
        JsonObject o = toJSON(jsonStr);
        Pizza p = Pizza.create(o);
        Delivery d = Delivery.create(o);
        Order ord = new Order(p , d);
        ord.setOrderId(o.getString("orderId"));
        ord.setTotalCost(o.getJsonNumber("total").doubleValue());
        return ord;
    }

    public JsonObject toJSON(){
        return Json.createObjectBuilder()
                .add("orderId", this.getOrderId())
                .add("name", this.getName())
                .add("address", this.getAddress())
                .add("phone", this.getPhone())
                .add("rush", this.getRush())
                .add("comments", this.getComments())
                .add("pizza", this.getPizzaName())
                .add("size", this.getSize())
                .add("quantity", this.getQuantity())
                .add("total", this.getTotalCost())
                .build();
    }

    
}
