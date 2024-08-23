package com.blps.controllers;

import com.blps.entity.User;
import com.blps.model.AdvertisementResponse;
import com.blps.model.BookingRequest;
import com.blps.model.InfoResponse;
import com.blps.services.AdvertisementService;
import com.blps.services.BookingService;
import com.blps.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer")
public class BuyerController {

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    BookingService bookingService;

    @Autowired
    UserService userService;

    @GetMapping("/getAdvert")
    public AdvertisementResponse getAdvertisement(){
        return advertisementService.getAll();
    }

    @PostMapping("/booking")
    public InfoResponse bookFlat(@RequestBody BookingRequest bookingRequest){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByUsername(name);

        if(advertisementService.checkExistence(bookingRequest.getAdvert_id())){
            if(bookingService.bookFlatByAdvertId(bookingRequest,
                    advertisementService.getById(bookingRequest.getAdvert_id()).get().getId(), user)){
                return new InfoResponse("Бронирование успешно создано", 0);
            }
            else {
                return new InfoResponse("Данная дата бронирования занята", 1);
            }
        }
        else {
            return new InfoResponse("Объявления с id '" + bookingRequest.getAdvert_id() + "' нет", 1);
        }
    }

    @PostMapping("/provided")
    public InfoResponse provided(@RequestBody long id){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByUsername(name);

        if(bookingService.getBooking(id).isApproved()){
            if(bookingService.getBooking(id).getBuyer_id() == user.getId()){
                bookingService.checkedIn(bookingService.getBooking(id));
                return new InfoResponse("Пользователь с id брони '" + id + "' заселен", 0);
            }
            else {
                return new InfoResponse("Данное бронирование не принадлежит вам", 1);
            }
        }
        else {
            return new InfoResponse("Бронирование с id '" + id + "' не было одобрено", 1);
        }
    }
}
