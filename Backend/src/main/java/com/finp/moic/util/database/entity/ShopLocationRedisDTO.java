package com.finp.moic.util.database.entity;


import org.springframework.data.geo.Point;

public class ShopLocationRedisDTO {

    private String mainCategory;
    private String category;
    private String location;
    private String address;
    private String guName;
    private Point point;

    public ShopLocationRedisDTO(String mainCategory, String category,
                                String location, String address,
                                String guName, double latitude, double longitude) {
        this.mainCategory = mainCategory;
        this.category = category;
        this.location = location;
        this.address = address;
        this.guName = guName;
        this.point = new Point(latitude,longitude);
    }
}
