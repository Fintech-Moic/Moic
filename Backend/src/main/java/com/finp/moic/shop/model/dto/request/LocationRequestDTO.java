package com.finp.moic.shop.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class LocationRequestDTO {

    private double latitude;
    private double longitude;

    public LocationRequestDTO() {
    }

    @Builder
    public LocationRequestDTO(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
