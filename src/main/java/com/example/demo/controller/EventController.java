package com.example.demo.controller;

import com.example.demo.dto.EventDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
        return eventService.createEvent(eventDTO);
    }

    @GetMapping("/search")
    public List<Event> searchEvents(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String location,
                                    @RequestParam(required = false) Double price) {
        List<Event> foundEvents = eventService.searchEvents(name, location, price);
        return foundEvents;
    }

    @GetMapping("/{eventId}")
    public Event getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @GetMapping("/{eventId}/participants")
    public List<User> getParticipantsByEventId(@PathVariable Long eventId) {
        return eventService.getParticipantsByEventId(eventId);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable Long eventId, @RequestBody Event updatedEvent) {
        return eventService.updateEvent(eventId, updatedEvent);
    }

    @DeleteMapping("/{eventId}")
    public boolean deleteEvent(@PathVariable Long eventId) {
        return eventService.deleteEvent(eventId);
    }
}

