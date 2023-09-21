package com.finp.moic.shop.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ShopDetailRequestDTO {

    @NotNull
    @NotBlank
    private String shopName;

    @NotNull
    private String shopLocation;

    public ShopDetailRequestDTO() {
    }

    @Builder
    public ShopDetailRequestDTO(@NotNull String shopName, @NotNull String shopLocation) {
        this.shopName = shopName;
        this.shopLocation = shopLocation;
    }
}
