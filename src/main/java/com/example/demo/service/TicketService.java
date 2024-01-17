package com.example.demo.service;

import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.Event;
import com.example.demo.entity.Portfolio;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    private PortfolioRepository portfolioRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, EventRepository eventRepository, UserRepository userRepository, PortfolioRepository portfolioRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public Ticket createTicket(TicketDTO ticketDTO) {
        Event event = eventRepository.findById(ticketDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found with ID: " + ticketDTO.getEventId()));

        User user = userRepository.findById(ticketDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + ticketDTO.getUserId()));

        Portfolio portfolio = portfolioRepository.findByUserId(ticketDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Portfolio not found with USER ID: " + ticketDTO.getUserId()));

        Ticket ticket = new Ticket();
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setCanceled(ticketDTO.isCanceled());
        ticket.setEvent(event);
        ticket.setUser(user);

        portfolio.getEvents().add(event);

        event.getParticipants().add(user);

        eventRepository.save(event);
        userRepository.save(user);
        ticketRepository.save(ticket);

        return ticket;
    }


    public Ticket getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId).orElse(null);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicket(Long ticketId, Ticket updatedTicket) {
        return ticketRepository.findById(ticketId)
                .map(existingTicket -> {
                    existingTicket.setCanceled(updatedTicket.getCanceled());
                    return ticketRepository.save(existingTicket);
                })
                .orElse(null);
    }

    public boolean deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
        return true;
    }
}
