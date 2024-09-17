package com.blps.services;

import com.blps.entity.Advertisement;
import com.blps.entity.User;
import com.blps.model.AdvertisementRequest;
import com.blps.model.AdvertisementResponse;
import com.blps.repository.AdvertisementRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    AdvertisementRepository advertisementRepository;

    @Transactional(transactionManager = "transactionManager")
    public void createAdvertisement(@NonNull AdvertisementRequest request, Long owner_id) {
        Advertisement newAdvert = new Advertisement();
        newAdvert.setAddress(request.getAddress());
        newAdvert.setDescription(request.getDescription());
        newAdvert.setPrice(request.getPrice());
        newAdvert.setOwner_id(owner_id);
        advertisementRepository.save(newAdvert);
    }

    @Transactional(readOnly = true)
    public AdvertisementResponse getAll() {
        final var res = new AdvertisementResponse(advertisementRepository.findAll().stream().collect(Collectors.toList()));
        return res;
    }

    @Transactional(readOnly = true)
    public Optional<Advertisement> getById(Long id) {
        return advertisementRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public boolean checkExistence(Long id) {
        return advertisementRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean checkOwner(Long id, User user) {
        Optional<Advertisement> opt = advertisementRepository.findById(id);
        return opt.map(advertisement -> advertisement.getOwner_id() == user.getId()).orElse(false);
    }
}
