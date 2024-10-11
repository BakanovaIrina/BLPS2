package com.blps.services;

import com.blps.entity.Booking;
import com.blps.entity.User;
import com.blps.model.BookingRequest;
import com.blps.repository.BookingRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(transactionManager = "transactionManager")
    public boolean bookFlatByAdvertId(BookingRequest bookingRequest, Long advertId, User user){
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


    public Booking createBooking(@NonNull BookingRequest bookingRequest, Long buyer_id){
        Booking booking = new Booking();
        booking.setAdvert_id(bookingRequest.getAdvert_id());
        booking.setBuyer_id(buyer_id);
        booking.setApproved(false);
        booking.setCheckedIn(false);
        booking.setDelete(false);
        booking.setBookingDate1(bookingRequest.getBookingDate1());
        booking.setBookingDate2(bookingRequest.getBookingDate2());

        return booking;
    }

    public void save(Booking booking){
        bookingRepository.save(booking);
    }

    public boolean checkExistence(Long id){
        Optional<Booking> opt = bookingRepository.findById(id);
        if(opt.isPresent()){
            return true;
        }
        else {
            return false;
        }
    }

    @Transactional(transactionManager = "transactionManager")
    public boolean rejectBooking(Long id){
        Booking booking = bookingRepository.getById(id);
        if(booking.isApproved()){
            booking.setApproved(false);
            return true;
        }
        else
            return false;
    }

    @Transactional
    public void setApproved(Booking booking, boolean approved){
        booking.setApproved(approved);
        bookingRepository.save(booking);
    }

    public void checkedIn(Booking booking){
        booking.setCheckedIn(true);
        bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings(long owner_id){
        return bookingRepository.findBookingsByOwnerIdAndConditions(owner_id);
    }

    public Booking getBooking(long booking_id){
        return bookingRepository.getById(booking_id);
    }

    public Long getBuyerId(long booking_id){
        Booking booking = bookingRepository.getById(booking_id);
        return booking.getBuyer_id();
    }

    public List<Booking> getBookingsBeforeDate(Timestamp timestamp){
        return bookingRepository.findAllActiveBookingsBeforeDate(timestamp);
    }

    public void markBookingAsDeleted(Booking booking) {
        booking.setDelete(true);
        bookingRepository.save(booking);
    }

}
