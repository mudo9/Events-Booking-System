package com.eventbooking.events.repository;

import com.eventbooking.events.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
