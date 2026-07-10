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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Test
    void shouldThrowExceptionIfNumberOfTicketsIsLessThanOrEqualToZero() {
        User user = new User();
        user.setId(1L);

        Event event = new Event();
        event.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(1L, 1L, 0));
    }

    @Test
    void shouldSaveTheBookingIfValidParametersAreGiven() {
        User user = new User();
        user.setId(1L);

        Event event = new Event();
        event.setId(1L);
        event.setCapacity(30);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        bookingService.createBooking(1L, 1L, 5);

        verify(bookingRepository).save(any(Booking.class));
    }
    @Test
    void shouldDeleteAnExistingBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setNumberOfTickets(4);

        User user = new User();
        user.setUsername("mudo");

        Event event = new Event();
        event.setCapacity(50);
        booking.setUser(user);
        booking.setEvent(event);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        bookingService.deleteBooking(1L, "mudo");
        verify(bookingRepository).deleteById(1L);
    }

    @Test
    void shouldThrowErrorIfBookingToBeDeletedDoesNotExist() {
        when(bookingRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookingService.deleteBooking(2L, "mudo"));
    }

    @Test
    void shouldThrowErrorIfUserProposingADeleteDoesNotOwnTheBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        User user = new User();
        user.setUsername("mudo");
        booking.setUser(user);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        assertThrows(RuntimeException.class, () -> bookingService.deleteBooking(1L, "James"));
        verify(bookingRepository, never()).deleteById(anyLong());
    }
}
