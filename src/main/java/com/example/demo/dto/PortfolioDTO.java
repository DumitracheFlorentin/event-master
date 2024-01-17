package com.example.demo.dto;

public class PortfolioDTO {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public PortfolioDTO(Long userId) {
        this.userId = userId;
    }

    public PortfolioDTO() {
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

