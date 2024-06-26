package com.example.userdemo.Service;

import com.example.userdemo.Exception.UserNotFoundException;
import com.example.userdemo.Model.User;
import com.example.userdemo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(String userId, Map<String,Object> fields) {
        if (userRepository.findById(userId).isEmpty())
            throw new UserNotFoundException(("Requested user ID is not found."));
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, user.get(), value);
            });
            return userRepository.save(user.get());
        }
        return null;
    }

    public User getUserById(String userId) {
        if(userRepository.findById(userId).isEmpty())
            throw new UserNotFoundException(("Requested user ID is not found."));
        return userRepository.findById(userId).get();
    }

    public void deleteUserById(String userId) {
        if(userRepository.findById(userId).isEmpty())
            throw new UserNotFoundException(("Requested user ID is not found."));
        userRepository.deleteById(userId);
    }
}
