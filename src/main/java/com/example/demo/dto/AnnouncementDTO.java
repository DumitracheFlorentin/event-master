package com.example.demo.dto;

import java.util.Date;

public class AnnouncementDTO {
    private String title;
    private String content;
    private Date datePosted;
    private Long eventId;

    public AnnouncementDTO() {
    }

    public AnnouncementDTO(String title, String content, Date datePosted, Long eventId) {
        this.title = title;
        this.content = content;
        this.datePosted = datePosted;
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
