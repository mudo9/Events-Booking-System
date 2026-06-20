package com.eventbooking.events.service;

import com.eventbooking.events.dto.UserResponse;
import com.eventbooking.events.model.User;
import com.eventbooking.events.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> properUserValues = new ArrayList<>();
        for (User user : users) {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            UserResponse userResponse = new UserResponse(firstName, lastName, email);
            properUserValues.add(userResponse);
        }
        return properUserValues;
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User with id: " + id + " doesn't exist."));
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
