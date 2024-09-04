package com.blps.repository;

import com.blps.entity.Advertisement;
import com.blps.entity.Booking;
import com.blps.model.AdvertisementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
