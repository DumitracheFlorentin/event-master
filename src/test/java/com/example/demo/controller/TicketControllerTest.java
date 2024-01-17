package com.example.demo.controller;

import com.example.demo.dto.TicketDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TicketController(ticketService)).build();
    }

    @Test
    public void testCreateTicket() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setPrice(30);
        ticketDTO.setUserId(16L);
        ticketDTO.setEventId(11L);

        when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(new Ticket());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"price\": 30, \"userId\": 16, \"eventId\": 11}"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andReturn();

        verify(ticketService, times(1)).createTicket(any(TicketDTO.class));
    }

    @Test
    public void testGetTicketById() throws Exception {
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        when(ticketService.getTicketById(eq(1L))).thenReturn(ticket);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets/1"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L)) // Verify the ticket's ID in the response
                .andReturn();

        verify(ticketService, times(1)).getTicketById(eq(1L));
    }

    @Test
    public void testGetAllTickets() throws Exception {
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketService.getAllTickets()).thenReturn(tickets);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/tickets"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L)) // Verify the first ticket's ID in the response
                .andExpect(jsonPath("$[1].id").value(2L)) // Verify the second ticket's ID in the response
                .andReturn();

        verify(ticketService, times(1)).getAllTickets();
    }

    @Test
    public void testUpdateTicket() throws Exception {
        Ticket updatedTicket = new Ticket();
        updatedTicket.setCanceled(true);

        when(ticketService.updateTicket(eq(1L), any(Ticket.class))).thenReturn(updatedTicket);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/api/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"canceled\": true}"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.canceled").value(true)) // Verify the updated canceled status in the response
                .andReturn();

        verify(ticketService, times(1)).updateTicket(eq(1L), any(Ticket.class));
    }

    @Test
    public void testDeleteTicket() throws Exception {
        when(ticketService.deleteTicket(eq(1L))).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/tickets/1"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andReturn();

        verify(ticketService, times(1)).deleteTicket(eq(1L));
    }
}
