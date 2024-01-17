package com.example.demo.dto;

import java.util.Date;

public class EventDTO {
    private String name;
    private String location;
    private Date date;
    private double price;
    private Long organizerId;

    public EventDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public EventDTO(String name, String location, Date date, double price, Long organizerId) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.price = price;
        this.organizerId = organizerId;
    }

}
