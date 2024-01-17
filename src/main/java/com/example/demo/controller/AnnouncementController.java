package com.example.demo.controller;

import com.example.demo.dto.AnnouncementDTO;
import com.example.demo.entity.Announcement;
import com.example.demo.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @PostMapping
    public ResponseEntity<Announcement> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        Announcement newAnnouncement = announcementService.createAnnouncement(announcementDTO);
        return ResponseEntity.status(201).body(newAnnouncement);
    }

    @GetMapping("/{announcementId}")
    public ResponseEntity<Announcement> getAnnouncementById(@PathVariable Long announcementId) {
        Announcement announcement = announcementService.getAnnouncementById(announcementId);
        if (announcement != null) {
            return ResponseEntity.ok(announcement);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getAllAnnouncements() {
        List<Announcement> announcements = announcementService.getAllAnnouncements();
        return ResponseEntity.ok(announcements);
    }

    @PutMapping("/{announcementId}")
    public ResponseEntity<Announcement> updateAnnouncement(@PathVariable Long announcementId, @RequestBody AnnouncementDTO updatedAnnouncementDTO) {
        Announcement updatedAnnouncement = announcementService.updateAnnouncement(announcementId, updatedAnnouncementDTO);
        if (updatedAnnouncement != null) {
            return ResponseEntity.ok(updatedAnnouncement);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{announcementId}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long announcementId) {
        boolean deleted = announcementService.deleteAnnouncement(announcementId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
