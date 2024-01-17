package com.example.demo.service;

import com.example.demo.dto.EventDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.User;
import com.example.demo.enums.UserRole;
import com.example.demo.exception.UserCreationException;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }
    public ResponseEntity<Event> createEvent(EventDTO event) {
        User user = userRepository.findById(event.getOrganizerId()).orElse(null);

        if(user.getRole() == UserRole.NORMAL) {
            throw new UserCreationException("User must be organizer");
        }

        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setLocation(event.getLocation());
        newEvent.setDate(event.getDate());
        newEvent.setPrice(event.getPrice());
        newEvent.setOrganizer(user);

        eventRepository.save(newEvent);

        return ResponseEntity.status(201).body(newEvent);
    }

    public List<Event> searchEvents(String name, String location, Double price) {
        List<Event> foundEvents = eventRepository.findByCriteria(name, location, price);
        return foundEvents;
    }


    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElse(null);
    }


    public List<User> getParticipantsByEventId(Long eventId) {
        return eventRepository.findParticipantsByEventId(eventId);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event updateEvent(Long eventId, Event updatedEvent) {
        return eventRepository.findById(eventId)
                .map(existingEvent -> {
                    if (updatedEvent.getLocation() != null) {
                        existingEvent.setLocation(updatedEvent.getLocation());
                    }
                    if (updatedEvent.getName() != null) {
                        existingEvent.setName(updatedEvent.getName());
                    }
                    if (updatedEvent.getDate() != null) {
                        existingEvent.setDate(updatedEvent.getDate());
                    }
                    return eventRepository.save(existingEvent);
                })
                .orElse(null);
    }


    public boolean deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
        return true;
    }
}
