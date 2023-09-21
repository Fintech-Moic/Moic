package com.finp.moic.shop.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TestShopResponseDTO implements Comparable<TestShopResponseDTO> {

    private String shopName;
    private String address;
    private double latitude;
    private double longitude;
    private double distance;

    public TestShopResponseDTO() {
    }

    @Builder
    public TestShopResponseDTO(String shopName, String address,
                               double latitude, double longitude, double distance) {
        this.shopName = shopName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    @Override
    public int compareTo(TestShopResponseDTO o) {
        return Double.compare(this.distance,o.distance);
    }
}
