package com.example.demo.service;

import com.example.demo.dto.PortfolioDTO;
import com.example.demo.entity.Portfolio;
import com.example.demo.entity.User;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private UserRepository userRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
    }

    public Portfolio addToPortfolio(PortfolioDTO portfolioDTO) {
        User user = userRepository.findById(portfolioDTO.getUserId()).orElse(null);

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio getPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElse(null);
    }

    public Portfolio removeFromPortfolio(Long portfolioId) {
        Portfolio deletedPortfolio = portfolioRepository.findById(portfolioId).orElse(null);
        if (deletedPortfolio != null) {
            portfolioRepository.deleteById(portfolioId);
        }
        return deletedPortfolio;
    }

    public List<Portfolio> getAllPortfolioEvents() {
        return portfolioRepository.findAll();
    }
}

