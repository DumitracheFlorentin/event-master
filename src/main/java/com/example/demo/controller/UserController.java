package com.example.demo.controller;

import com.example.demo.dto.PortfolioDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserCreationException;
import com.example.demo.service.PortfolioService;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PortfolioService portfolioService;

    @Autowired
    public UserController(UserService userService, PortfolioService portfolioService) {
        this.userService = userService;
        this.portfolioService = portfolioService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getUsername() == null || userDTO.getPassword() == null || userDTO.getRole() == null || userDTO.getEmail() == null) {
            throw new UserCreationException("Complete all fields");
        }

        UserDTO createdUser = userService.createUser(userDTO);

        PortfolioDTO newPortfolio = new PortfolioDTO(createdUser.getId());
        portfolioService.addToPortfolio(newPortfolio);

        return ResponseEntity.status(201).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username == null || password == null) {
            throw new UserCreationException("Username and password are required");
        }

        if (userService.authenticateUser(username, password)) {
            String token = JwtUtil.generateToken(username);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.status(200).body(response);
        } else {
            return ResponseEntity.status(400).body("Incorrect name or password.");
        }
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Long userId) {
        UserDTO userDTO = userService.getUserById(userId);
        if (userDTO == null) {
            throw new UserCreationException("User not found with ID: " + userId);
        }
        return userDTO;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<UserDTO> userDTOs = userService.getAllUsers();
        // Convert UserDTOs if needed
        return userDTOs;
    }

    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable Long userId, @RequestBody UserDTO updatedUserDTO) {
        UserDTO updatedUser = userService.updateUser(userId, updatedUserDTO);
        if (updatedUser == null) {
            throw new UserCreationException("User not found with ID: " + userId);
        }
        return updatedUser;
    }

    @DeleteMapping("/username/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        boolean deleted = userService.deleteUserByUsername(username);
        if (deleted) {
            return ResponseEntity.status(200).body("User with username " + username + " has been deleted.");
        } else {
            return ResponseEntity.status(404).body("User with username " + username + " not found.");
        }
    }


    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }
}
