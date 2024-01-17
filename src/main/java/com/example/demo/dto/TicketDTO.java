package com.example.demo.dto;

public class TicketDTO {
    private double price;
    private Long userId;
    private Long eventId;
    private boolean canceled = false;

    public TicketDTO() {
    }

    public TicketDTO(double price, Long userId, Long eventId) {
        this.price = price;
        this.userId = userId;
        this.eventId = eventId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}

