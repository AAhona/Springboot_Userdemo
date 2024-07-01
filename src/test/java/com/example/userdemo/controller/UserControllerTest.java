package com.example.userdemo.controller;

import com.example.userdemo.model.User;
import com.example.userdemo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreateUser() throws Exception{
        User user = new User("MP1", "Ahona", "abc@gmail.com", "123");
        when(userService.createUser(user)).thenReturn(user);
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception{
        User user = new User("MP1", "Ahona", "abc@gmail.com", "123");
        when(userService.getUserById("MP1")).thenReturn(user);
        mockMvc.perform(get("/user/{userId}", "MP1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("Ahona"));
    }

    @Test
    public void testUpdateUser() throws Exception{
        String userId = "MP1";
        User updatedUser = new User(userId, "Ahona", "newemail@gmail.com", "1234");

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "newemail@gmail.com");
        updates.put("phoneNumber", "1234");

        when(userService.updateUser(eq(userId), anyMap())).thenReturn(updatedUser);
        mockMvc.perform(patch("/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("newemail@gmail.com"))
                .andExpect(jsonPath("$.phoneNumber").value("1234"));
    }

    @Test
    public void testDeleteUserById() throws Exception{
        String userId = "MP1";
        doNothing().when(userService).deleteUserById(userId);
        mockMvc.perform(delete("/user/{userId}", userId))
                .andExpect(status().isOk());
    }
}
