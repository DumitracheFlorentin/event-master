package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.Portfolio;
import com.example.demo.entity.User;
import com.example.demo.repository.PortfolioRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private PortfolioRepository portfolioRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PortfolioRepository portfolioRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.portfolioRepository = portfolioRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        User user = new User(userDTO.getUsername(), encodedPassword, userDTO.getRole(),
                userDTO.getFirstName(), userDTO.getLastName(), userDTO.getEmail(),
                userDTO.getAddress(), userDTO.getDateOfBirth());

        User newUser = userRepository.save(user);

        UserDTO createdUser = new UserDTO();
        createdUser.setId(newUser.getId());
        createdUser.setUsername(newUser.getUsername());
        createdUser.setRole(newUser.getRole());
        createdUser.setFirstName(newUser.getFirstName());
        createdUser.setLastName(newUser.getLastName());
        createdUser.setEmail(newUser.getEmail());
        createdUser.setAddress(newUser.getAddress());
        createdUser.setDateOfBirth(newUser.getDateOfBirth());
        createdUser.setPassword(newUser.getPassword());

        return createdUser;
    }

    public boolean authenticateUser(String username, String password) {
        UserDTO user = getUserByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    public boolean deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            Portfolio userPortfolio = portfolioRepository.findByUserId(user.getId()).orElse(null);

            if (userPortfolio != null) {
                portfolioRepository.delete(userPortfolio);
            }

            userRepository.delete(user);
            return true;
        }
        return false;
    }



    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setPassword(user.getPassword());

        return userDTO;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRole(user.getRole());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setEmail(user.getEmail());
            userDTO.setAddress(user.getAddress());
            userDTO.setDateOfBirth(user.getDateOfBirth());
            userDTO.setPassword(user.getPassword());
            userDTOs.add(userDTO);

        }

        return userDTOs;
    }

    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        // Update user fields if provided in updatedUserDTO
        if (updatedUserDTO.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(updatedUserDTO.getPassword());
            user.setPassword(encodedPassword);
        }
        if (updatedUserDTO.getRole() != null) {
            user.setRole(updatedUserDTO.getRole());
        }
        if (updatedUserDTO.getFirstName() != null) {
            user.setFirstName(updatedUserDTO.getFirstName());
        }
        if (updatedUserDTO.getLastName() != null) {
            user.setLastName(updatedUserDTO.getLastName());
        }
        if (updatedUserDTO.getAddress() != null) {
            user.setAddress(updatedUserDTO.getAddress());
        }
        if (updatedUserDTO.getDateOfBirth() != null) {
            user.setDateOfBirth(updatedUserDTO.getDateOfBirth());
        }

        User updatedUser = userRepository.save(user);

        UserDTO updatedUserResponse = new UserDTO();
        updatedUserResponse.setId(updatedUser.getId());
        updatedUserResponse.setUsername(updatedUser.getUsername());
        updatedUserResponse.setRole(updatedUser.getRole());
        updatedUserResponse.setFirstName(updatedUser.getFirstName());
        updatedUserResponse.setLastName(updatedUser.getLastName());
        updatedUserResponse.setEmail(updatedUser.getEmail());
        updatedUserResponse.setAddress(updatedUser.getAddress());
        updatedUserResponse.setDateOfBirth(updatedUser.getDateOfBirth());
        updatedUserResponse.setPassword(updatedUser.getPassword());

        return updatedUserResponse;
    }

    public boolean deleteUser(Long userId) {
        userRepository.deleteById(userId);
        return true;
    }
}
