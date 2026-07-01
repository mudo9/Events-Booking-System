package com.eventbooking.events.service;

import com.eventbooking.events.model.Booking;
import com.eventbooking.events.model.Event;
import com.eventbooking.events.model.User;
import com.eventbooking.events.repository.BookingRepository;
import com.eventbooking.events.repository.EventRepository;
import com.eventbooking.events.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private BookingService bookingService;

    @Test
    void shouldReduceCapacityOfAnEventWhenCreatingABooking() {
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        event.setId(1L);
        event.setCapacity(100);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        Booking booking = bookingService.createBooking(1L, 1L, 3);
        assertEquals(97, event.getCapacity());

    }

    @Test
    void shouldThrowExceptionIfUserIsNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, ()-> bookingService.createBooking(1L, 1L, 20));
    }

    @Test
    void shouldThrowExceptionIfEventIsNotFound() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when (eventRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, 20));
    }

    @Test
    void shouldThrowExceptionIfNumberOfTicketsExceedsEventCapacity() {
        User user = new User();
        user.setId(1L);

        Event event = new Event();
        event.setId(1L);
        event.setCapacity(5);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        
        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, 6));
    }
}
