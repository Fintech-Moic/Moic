package com.finp.moic.shop.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class LocationResponseDTO {

    private List<TestShopResponseDTO> shop;
    private double time;

    public LocationResponseDTO() {
    }

    @Builder
    public LocationResponseDTO(List<TestShopResponseDTO> shop, double time) {
        this.shop = shop;
        this.time = time;
    }
}
