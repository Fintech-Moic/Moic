package com.finp.moic.util.database.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.geo.Point;

@Getter
@ToString
public class ShopLocationRedisDTO {

    private String mainCategory; //사용 여부 미정
    private String category;
    private String location;
    private String address;
    private String guName; //사용 여부 미정

    public ShopLocationRedisDTO() {
    }

    @Builder
    public ShopLocationRedisDTO(String mainCategory, String category,
                                String location, String address, String guName) {
        this.mainCategory = mainCategory;
        this.category = category;
        this.location = location;
        this.address = address;
        this.guName = guName;
    }
}
