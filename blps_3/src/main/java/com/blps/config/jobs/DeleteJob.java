package com.blps.config.jobs;

import com.blps.controllers.OwnerController;
import com.blps.entity.Booking;
import com.blps.services.BookingService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class DeleteJob implements Job {

    @Autowired
    BookingService bookingService;


    private static final Logger logger = LoggerFactory.getLogger(DeleteJob.class);

    @Override
    @Transactional
    public void execute(JobExecutionContext context) {

        logger.info("Monthly job executed at {}", context.getFireTime());
        List<Booking> bookings = bookingService.getBookingsBeforeDate(Timestamp.valueOf(LocalDateTime.now()));
        for(Booking booking : bookings){
            bookingService.markBookingAsDeleted(booking);

        }
    }
}

