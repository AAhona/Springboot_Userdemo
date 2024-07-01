package com.example.userdemo.service;

import com.example.userdemo.model.User;
import com.example.userdemo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;
    AutoCloseable autoCloseable;
    User user;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
        user = new User("MP1","Ahona","abc@gmail.com","111");
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    void testCreateUser(){
        //mock(User.class);
        //mock(UserRepository.class);

        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertThat(createdUser).isEqualTo(user);
    }

    @Test
    void testUpdateUser(){
        String userId = "MP1";
        User updatedUser = new User(userId, "Ahona", "new_email@gmail.com", "222");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updatedUser);

        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "new_email@gmail.com");
        updates.put("phoneNumber", "222");

        User result = userService.updateUser(userId, updates);

        assertThat(result.getEmail()).isEqualTo("new_email@gmail.com");
        assertThat(result.getPhoneNumber()).isEqualTo("222");
    }

    @Test
    void testGetCloudUser() {
        mock(User.class);
        mock(UserRepository.class);

        when(userRepository.findById("MP1")).thenReturn(Optional.ofNullable(user));

        assertThat(userService.getUserById("MP1").getUserName())
                .isEqualTo(user.getUserName());
    }
    @Test
    void testDeleteUser(){
        String userId = "MP1";
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUserById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }




}
