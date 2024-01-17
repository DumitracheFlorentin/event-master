package com.example.demo.controller;

import com.example.demo.dto.PortfolioDTO;
import com.example.demo.entity.Portfolio;
import com.example.demo.service.PortfolioService;
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

public class PortfolioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PortfolioService portfolioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new PortfolioController(portfolioService)).build();
    }

    @Test
    public void testAddToPortfolio() throws Exception {
        PortfolioDTO portfolioDTO = new PortfolioDTO();
        portfolioDTO.setUserId(21L);

        when(portfolioService.addToPortfolio(any(PortfolioDTO.class))).thenReturn(new Portfolio());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\": 21}"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andReturn();

        verify(portfolioService, times(1)).addToPortfolio(any(PortfolioDTO.class));
    }

    @Test
    public void testRemoveFromPortfolio() throws Exception {
        when(portfolioService.removeFromPortfolio(eq(2L))).thenReturn(new Portfolio());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/api/portfolio/2"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andReturn();

        verify(portfolioService, times(1)).removeFromPortfolio(eq(2L));
    }

    @Test
    public void testGetPortfolioById() throws Exception {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(4L);

        when(portfolioService.getPortfolioById(eq(4L))).thenReturn(portfolio);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/portfolio/4"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(4L)) // Verify the portfolio's ID in the response
                .andReturn();

        verify(portfolioService, times(1)).getPortfolioById(eq(4L));
    }

    @Test
    public void testGetAllPortfolioEvents() throws Exception {
        Portfolio portfolio1 = new Portfolio();
        portfolio1.setId(1L);
        Portfolio portfolio2 = new Portfolio();
        portfolio2.setId(2L);
        List<Portfolio> portfolios = Arrays.asList(portfolio1, portfolio2);

        when(portfolioService.getAllPortfolioEvents()).thenReturn(portfolios);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/portfolio"))
                .andExpect(status().isOk()) // Expect HTTP status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L)) // Verify the first portfolio's ID in the response
                .andExpect(jsonPath("$[1].id").value(2L)) // Verify the second portfolio's ID in the response
                .andReturn();

        verify(portfolioService, times(1)).getAllPortfolioEvents();
    }
}
