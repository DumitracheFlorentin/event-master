package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class EventControllerTest {
    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new EventController(eventService)).build();
    }

    @Test
    public void testCreateEvent() throws Exception {
        EventDTO newEvent = new EventDTO();
        newEvent.setName("Test Event");
        newEvent.setLocation("Test Location");
        newEvent.setDate(new Date());
        newEvent.setPrice(10.0);
        newEvent.setOrganizerId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newEvent)))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }

    @Test
    public void testSearchEvents() throws Exception {
        List<Event> events = new ArrayList<>();

        Mockito.when(eventService.searchEvents(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(events.size()));
    }

    @Test
    public void testGetParticipantsByEventId() throws Exception {
        Long eventId = 1L;
        List<User> participants = new ArrayList<>();

        Mockito.when(eventService.getParticipantsByEventId(eventId)).thenReturn(participants);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events/{eventId}/participants", eventId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(participants.size()));
    }

    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = new ArrayList<>();

        Mockito.when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(events.size()));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        Long eventId = 1L;

        Mockito.when(eventService.deleteEvent(eventId)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/events/{eventId}", eventId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }
}
