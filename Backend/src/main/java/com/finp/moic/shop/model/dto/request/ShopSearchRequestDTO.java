package com.finp.moic.shop.model.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShopSearchRequestDTO {

    private String keyword;
    private double latitude;
    private double longitude;

    public ShopSearchRequestDTO() {
    }

    @Builder
    public ShopSearchRequestDTO(String keyword, double latitude, double longitude) {
        this.keyword = keyword;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
