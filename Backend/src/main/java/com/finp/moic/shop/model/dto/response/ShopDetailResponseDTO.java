package com.finp.moic.shop.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ShopDetailResponseDTO {

    private String category;
    private String shopName;
    private String shopLocation;
    private String address;
    private double latitude;
    private double longitude;
    private List<BenefitResponseDTO> benefits;
    private List<GiftResponseDTO> gifts;

    public ShopDetailResponseDTO() {
    }

    @Builder
    public ShopDetailResponseDTO(String category, String shopName, String shopLocation,
                                 String address, double latitude, double longitude,
                                 List<BenefitResponseDTO> benefits, List<GiftResponseDTO> gifts) {
        this.category = category;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.benefits = benefits;
        this.gifts = gifts;
    }
}
