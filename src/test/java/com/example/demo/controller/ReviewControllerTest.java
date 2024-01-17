package com.example.demo.controller;

import com.example.demo.dto.ReviewDTO;
import com.example.demo.entity.Review;
import com.example.demo.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReviewService reviewService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new ReviewController(reviewService)).build();
    }

    @Test
    public void testCreateReview() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setContent("test");
        reviewDTO.setRating(5);
        reviewDTO.setEventId(10L);
        reviewDTO.setUserId(13L);

        when(reviewService.createReview(any(ReviewDTO.class))).thenReturn(ResponseEntity.status(201).body(new Review()));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"test\", \"rating\": 5, \"eventId\": 10, \"userId\": 13}"))
                .andExpect(status().isCreated()) // Expect HTTP status 201 Created
                .andReturn();

        verify(reviewService, times(1)).createReview(any(ReviewDTO.class));
    }

    @Test
    public void testGetReviewById() throws Exception {
        Review review = new Review();
        review.setId(1L);

        when(reviewService.getReviewById(eq(1L))).thenReturn(review);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/reviews/1"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L)) // Verify the review's ID in the response
                .andReturn();

        verify(reviewService, times(1)).getReviewById(eq(1L));
    }

    @Test
    public void testGetAllReviews() throws Exception {
        Review review1 = new Review();
        review1.setId(1L);
        Review review2 = new Review();
        review2.setId(2L);
        List<Review> reviews = Arrays.asList(review1, review2);

        when(reviewService.getAllReviews()).thenReturn(reviews);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/reviews"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L)) // Verify the first review's ID in the response
                .andExpect(jsonPath("$[1].id").value(2L)) // Verify the second review's ID in the response
                .andReturn();

        verify(reviewService, times(1)).getAllReviews();
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review updatedReview = new Review();
        updatedReview.setRating(2);

        when(reviewService.updateReview(eq(2L), any(Review.class))).thenReturn(updatedReview);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/reviews/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\": 2}"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.rating").value(2)) // Verify the updated rating in the response
                .andReturn();

        verify(reviewService, times(1)).updateReview(eq(2L), any(Review.class));
    }

    @Test
    public void testDeleteReview() throws Exception {
        when(reviewService.deleteReview(eq(1L))).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/reviews/1"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andReturn();

        verify(reviewService, times(1)).deleteReview(eq(1L));
    }
}
