package com.eventbooking.events.service;

import com.eventbooking.events.model.Booking;
import com.eventbooking.events.model.Event;
import com.eventbooking.events.model.User;
import com.eventbooking.events.repository.BookingRepository;
import com.eventbooking.events.repository.EventRepository;
import com.eventbooking.events.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.bookingRepository =  bookingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public Booking createBooking(Long userId, Long eventId, int numberOfTickets) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(()-> new RuntimeException("Event not found with id: " + eventId));

        if (numberOfTickets > event.getCapacity()) {
            throw new RuntimeException("Not enough capacity. Only " + event.getCapacity() + " tickets left.");
        }

        if (numberOfTickets <= 0) {
            throw new RuntimeException("Number of tickets must be atleast 1");
        }

        event.setCapacity((event.getCapacity() - numberOfTickets));
        eventRepository.save(event);

        Booking booking = new Booking();
        booking.setNumberOfTickets(numberOfTickets);
        booking.setUser(user);
        booking.setEvent(event);
        return bookingRepository.save(booking);
    }

    public void deleteBooking(Long id, String username) {
        Booking booking = bookingRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        if (!booking.getUser().getUsername().equals(username)) {
            throw new RuntimeException("You can only cancel your own booking");
        }
        Event event = booking.getEvent();
        event.setCapacity(event.getCapacity() + booking.getNumberOfTickets());
        eventRepository.save(event);
        bookingRepository.deleteById(id);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
