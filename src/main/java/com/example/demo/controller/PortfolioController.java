package com.example.demo.controller;

import com.example.demo.dto.PortfolioDTO;
import com.example.demo.entity.Portfolio;
import com.example.demo.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping
    public Portfolio addToPortfolio(@RequestBody PortfolioDTO portfolioDTO) {
        return portfolioService.addToPortfolio(portfolioDTO);
    }

    @DeleteMapping("/{portfolioId}")
    public void removeFromPortfolio(@PathVariable Long portfolioId) {
        portfolioService.removeFromPortfolio(portfolioId);
    }

    @GetMapping("/{portfolioId}")
    public Portfolio getPortfolioById(@PathVariable Long portfolioId) {
        return portfolioService.getPortfolioById(portfolioId);
    }


    @GetMapping
    public List<Portfolio> getAllPortfolioEvents() {
        return portfolioService.getAllPortfolioEvents();
    }
}

