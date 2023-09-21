package com.finp.moic.shop.model.dto.response;

import com.finp.moic.shop.model.entity.Shop;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShopSearchResponseDTO {

    private String category;
    private String shopName;
    private String shopLocation;
    private String address;
    private boolean benefits;
    private boolean gifts;

    public ShopSearchResponseDTO() {
    }

    @Builder
    public ShopSearchResponseDTO(String category, String shopName, String shopLocation,
                                 String address, boolean benefits, boolean gifts) {
        this.category = category;
        this.shopName = shopName;
        this.shopLocation = shopLocation;
        this.address = address;
        this.benefits = benefits;
        this.gifts = gifts;
    }
}
