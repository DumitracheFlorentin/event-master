package com.example.demo.service;

import com.example.demo.dto.AnnouncementDTO;
import com.example.demo.entity.Announcement;
import com.example.demo.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public Announcement createAnnouncement(AnnouncementDTO announcementDTO) {
        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDTO.getTitle());
        announcement.setContent(announcementDTO.getContent());

        return announcementRepository.save(announcement);
    }

    public Announcement getAnnouncementById(Long announcementId) {
        return announcementRepository.findById(announcementId).orElse(null);
    }

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Announcement updateAnnouncement(Long announcementId, AnnouncementDTO updatedAnnouncementDTO) {
        Announcement existingAnnouncement = announcementRepository.findById(announcementId).orElse(null);
        if (existingAnnouncement != null) {
            if (updatedAnnouncementDTO.getContent() != null) {
                existingAnnouncement.setContent(updatedAnnouncementDTO.getContent());
            }
            if (updatedAnnouncementDTO.getTitle() != null) {
                existingAnnouncement.setTitle(updatedAnnouncementDTO.getTitle());
            }

            return announcementRepository.save(existingAnnouncement);
        }
        return null;
    }

    public boolean deleteAnnouncement(Long announcementId) {
        Announcement announcementToDelete = announcementRepository.findById(announcementId).orElse(null);
        if (announcementToDelete != null) {
            announcementRepository.delete(announcementToDelete);
            return true;
        }
        return false;
    }
}
