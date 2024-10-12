package com.blps.model;

import com.blps.entity.Advertisement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementResponse {
    private List<Advertisement> advertisementList;
}
