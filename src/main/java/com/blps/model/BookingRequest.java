package com.blps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private long advert_id;
    private Timestamp bookingDate1;
    private Timestamp bookingDate2;
}
