package com.blps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequest {
    private String address;

    private String description;

    private Integer price;
}
