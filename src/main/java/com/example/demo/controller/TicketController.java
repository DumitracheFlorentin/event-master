package com.example.demo.controller;

import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @GetMapping("/{ticketId}")
    public Ticket getTicketById(@PathVariable Long ticketId) {
        return ticketService.getTicketById(ticketId);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @PutMapping("/{ticketId}")
    public Ticket updateTicket(@PathVariable Long ticketId, @RequestBody Ticket updatedTicket) {
        return ticketService.updateTicket(ticketId, updatedTicket);
    }

    @DeleteMapping("/{ticketId}")
    public boolean deleteTicket(@PathVariable Long ticketId) {
        return ticketService.deleteTicket(ticketId);
    }
}
