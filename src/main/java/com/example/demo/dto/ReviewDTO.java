package com.example.demo.dto;

public class ReviewDTO {
    private String content;
    private int rating;
    private Long userId;
    private Long eventId;

    public ReviewDTO() {
    }

    public ReviewDTO(String content, int rating, Long userId, Long eventId) {
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.eventId = eventId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}

