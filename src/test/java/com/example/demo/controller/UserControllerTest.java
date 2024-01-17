package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.enums.UserRole;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testLoginSuccess() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "Test");
        credentials.put("password", "Test");

        when(userService.authenticateUser("Test", "Test")).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Test\", \"password\": \"Test\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("\"token\""));
    }

    @Test
    public void testLoginMissingPassword() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "Test");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Test\"}"))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertEquals("{\"status\":404,\"message\":\"Username and password are required\"}", responseContent);
    }

    @Test
    public void testLoginIncorrectCredentials() throws Exception {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "IncorrectUsername");
        credentials.put("password", "IncorrectPassword");

        when(userService.authenticateUser("IncorrectUsername", "IncorrectPassword")).thenReturn(false);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"IncorrectUsername\", \"password\": \"IncorrectPassword\"}"))
                .andExpect(status().isBadRequest()) // Expecting HTTP status 400 Bad Request
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        assertEquals("Incorrect name or password.", responseContent);
    }

    @Test
    public void testGetUserById() throws Exception {
        Long userId = 22L;

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/" + userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setUsername("FlorentinTestJava");
        newUser.setPassword("TestJava");
        newUser.setRole(UserRole.ORGANIZER);
        newUser.setEmail("florentintestjava@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testDeleteUserByUsername() throws Exception {
        String usernameToDelete = "FlorentinTestJava";

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/username/" + usernameToDelete))
                .andExpect(MockMvcResultMatchers.status().isOk());

        boolean userDeleted = userService.getUserByUsername(usernameToDelete) == null;
        assertTrue(userDeleted);
    }
}

