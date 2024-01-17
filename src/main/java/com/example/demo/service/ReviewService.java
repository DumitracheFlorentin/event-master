package com.example.demo.service;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, EventRepository eventRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    public ResponseEntity<Review> createReview(ReviewDTO reviewDTO) {
        Event event = eventRepository.findById(reviewDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + reviewDTO.getEventId()));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + reviewDTO.getUserId()));

        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());
        review.setEvent(event);
        review.setUser(user);

        return ResponseEntity.status(201).body(reviewRepository.save(review));
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review updateReview(Long reviewId, Review updatedReview) {
        return reviewRepository.findById(reviewId)
                .map(existingReview -> {
                    if (updatedReview.getContent() != null) {
                        existingReview.setContent(updatedReview.getContent());
                    }
                    if (updatedReview.getRating() != 0) {
                        existingReview.setRating(updatedReview.getRating());
                    }
                    return reviewRepository.save(existingReview);
                })
                .orElse(null);
    }

    public boolean deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return true;
    }
}

