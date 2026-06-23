package com.eventbooking.events.service;

import com.eventbooking.events.model.User;
import com.eventbooking.events.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).
                orElseThrow(()-> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User( //I wrote the full import statement here to not avoid confusion with model.User
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
}
