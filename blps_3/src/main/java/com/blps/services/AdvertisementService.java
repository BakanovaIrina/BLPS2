package com.blps.services;

import com.blps.entity.Advertisement;
import com.blps.entity.User;
import com.blps.model.AdvertisementRequest;
import com.blps.model.AdvertisementResponse;
import com.blps.repository.AdvertisementRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdvertisementService {
    @Autowired
    AdvertisementRepository advertisementRepository;

    public void createAdvertisement(@NonNull AdvertisementRequest request, Long owner_id){
        Advertisement newAdvert = new Advertisement();

        newAdvert.setAddress(request.getAddress());
        newAdvert.setDescription(request.getDescription());
        newAdvert.setPrice(request.getPrice());
        newAdvert.setOwner_id(owner_id);
        advertisementRepository.save(newAdvert);
    }

    public AdvertisementResponse getAll(){
        final var res = new AdvertisementResponse(advertisementRepository.findAll().stream().collect(Collectors.toList()));
        return res;
    }

    public Optional<Advertisement> getById(Long id){
        return advertisementRepository.findById(id);
    }

    public Advertisement getAdvertById(Long id){
        Optional<Advertisement> opt = advertisementRepository.findById(id);
        return opt.get();
    }


    public boolean checkExistence(Long id) {
        Optional<Advertisement> opt = advertisementRepository.findById(id);
        if(opt.isPresent()){
            return true;
        }
        else {
            return false;
        }
    }

    public long getOwnerId(Long id){
        Optional<Advertisement> opt = advertisementRepository.findById(id);
        return opt.get().getOwner_id();
    }

    public boolean checkOwner(Long id, User user){
        Optional<Advertisement> opt = advertisementRepository.findById(id);
        if(opt.isPresent()){
            Advertisement advertisement = opt.get();
            if(advertisement.getOwner_id() != user.getId()){
                return false;
            }
            return true;
        }
        else {
            return false;
        }
    }

    public Advertisement processAdvertisement(AdvertisementRequest request) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(request.getTitle());
        advertisement.setDescription(request.getDescription());
        advertisement.setPrice(request.getPrice());

        advertisementRepository.save(advertisement);

        // Start Camunda process
        Map<String, Object> variables = new HashMap<>();
        variables.put("advertisementId", advertisement.getId());
        runtimeService.startProcessInstanceByKey("advertisementProcess", variables);

        return advertisement;
    }
}
