package com.blps.services;

import com.blps.entity.Booking;
import com.blps.entity.User;
import com.blps.model.BookingRequest;
import com.blps.repository.BookingRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional(transactionManager = "transactionManager")
    public boolean bookFlatByAdvertId(BookingRequest bookingRequest, Long advertId, User user) {
        Booking booking = createBooking(bookingRequest, user.getId());
        List<Booking> list = bookingRepository.findBookingByAdvert(advertId);

        for (Booking b : list) {
            boolean isOverlapping = b.getBookingDate1().compareTo(booking.getBookingDate2()) <= 0 &&
                    b.getBookingDate2().compareTo(booking.getBookingDate1()) >= 0;

            if (isOverlapping) {
                return false;
            }
        }
        save(booking);
        return true;
    }

    @Transactional
    public Booking createBooking(@NonNull BookingRequest bookingRequest, Long buyer_id) {
        Booking booking = new Booking();
        booking.setAdvert_id(bookingRequest.getAdvert_id());
        booking.setBuyer_id(buyer_id);
        booking.setApproved(false);
        booking.setCheckedIn(false);
        booking.setBookingDate1(bookingRequest.getBookingDate1());
        booking.setBookingDate2(bookingRequest.getBookingDate2());

        return booking;
    }

    @Transactional
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public boolean checkExistence(Long id) {
        return bookingRepository.existsById(id);
    }

    @Transactional
    public boolean rejectBooking(Long id) {
        Booking booking = bookingRepository.getById(id);
        if (booking.isApproved()) {
            booking.setApproved(false);
            bookingRepository.save(booking);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void setApproved(Booking booking, boolean approved) {
        booking.setApproved(approved);
        bookingRepository.save(booking);
    }

    @Transactional
    public void checkedIn(Booking booking) {
        booking.setCheckedIn(true);
        bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getAllBookings(long owner_id) {
        return bookingRepository.findBookingsByOwnerIdAndConditions(owner_id);
    }

    @Transactional(readOnly = true)
    public Booking getBooking(long booking_id) {
        return bookingRepository.getById(booking_id);
    }
}