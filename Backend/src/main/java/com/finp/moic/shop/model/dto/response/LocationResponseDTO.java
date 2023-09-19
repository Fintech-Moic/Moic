package com.finp.moic.shop.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LocationResponseDTO {

    private String shopName;
    private String address;
    private double latitude;
    private double longitude;
    private double distance;
    private double time;

    @Builder
    public LocationResponseDTO(String shopName, String address,
                               double latitude, double longitude,
                               double distance, double time) {
        this.shopName = shopName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.time = time;
    }
}
