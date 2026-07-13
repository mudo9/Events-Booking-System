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
            Long id = user.getId();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String username = user.getUsername();
            UserResponse userResponse = new UserResponse(id, firstName, lastName, username);
            properUserValues.add(userResponse);
        }
        return properUserValues;
    }

    public UserResponse createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername());
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User with id: " + id + " doesn't exist."));
        return new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername());
    }
}
