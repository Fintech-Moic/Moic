package com.finp.moic.util.database.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Location")
public class ShopLocation {

    @Id
    private String id;
    private double latitude;
    private double longitude;

    public ShopLocation(String id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
