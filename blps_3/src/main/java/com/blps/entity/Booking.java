package com.blps.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Getter
    private long id;

    @Column(name = "advert_id")
    @Getter
    @Setter
    private long advert_id;

    @Column(name = "buyer_id")
    @Getter
    @Setter
    private long buyer_id;

    @Column(name = "approved")
    @Getter
    @Setter
    private boolean approved;

    @Column(name = "checked_in")
    @Getter
    @Setter
    private boolean checkedIn;

    @Column(name = "booking_date1")
    @Getter
    @Setter
    private Timestamp bookingDate1;

    @Column(name = "booking_date2")
    @Getter
    @Setter
    private Timestamp bookingDate2;

    @Column(name = "delete")
    @Getter
    @Setter
    private boolean delete;
}
