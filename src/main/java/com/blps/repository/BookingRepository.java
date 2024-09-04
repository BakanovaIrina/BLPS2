package com.blps.repository;

import com.blps.entity.Advertisement;
import com.blps.entity.Booking;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b JOIN Advertisement a ON b.advert_id = :advertId AND b.approved = true")
    public List<Booking> findBookingByAdvert(@Param("advertId") Long advertId);


    @Query("SELECT b.id FROM Booking b WHERE b.advert_id = :advertId AND b.buyer_id = :userId AND " +
            "b.bookingDate1 = :startDate AND b.bookingDate2 = :endDate AND b.approved = true")
    public Long findBookingIdByRequest(@Param("advertId") Long advertId,
                                   @Param("userId") Long userId,
                                   @Param("startDate") Timestamp startDate,
                                   @Param("endDate") Timestamp endDate);

    @Query("SELECT b FROM Booking b JOIN Advertisement a ON b.advert_id = a.id WHERE a.owner_id = :ownerId AND b.approved = false AND b.checkedIn = false")
    List<Booking> findBookingsByOwnerIdAndConditions(@Param("ownerId") long ownerId);

}


