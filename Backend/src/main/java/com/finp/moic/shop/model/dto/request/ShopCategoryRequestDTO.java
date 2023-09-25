package com.finp.moic.shop.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShopCategoryRequestDTO {

    private String category;
    private double latitude;
    private double longitude;

    public ShopCategoryRequestDTO() {
    }

    @Builder
    public ShopCategoryRequestDTO(String category, double latitude, double longitude) {
        this.category = category;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
