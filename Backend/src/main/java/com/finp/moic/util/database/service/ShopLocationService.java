package com.finp.moic.util.database.service;

import com.finp.moic.util.database.entity.ShopLocation;
import com.finp.moic.util.database.repository.ShopLocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ShopLocationService {

    private final ShopLocationRepository shopLocationRepository;

    @Autowired
    public ShopLocationService(ShopLocationRepository shopLocationRepository) {
        this.shopLocationRepository = shopLocationRepository;
    }

//    public void saveLocation(String id, double latitude, double longitude) {
//        ShopLocation location = new ShopLocation(id, new Point(latitude, longitude));
//        shopLocationRepository.save(location);
//    }
//
//    public GeoResults<ShopLocation> findLocationsWithinRadius(double latitude, double longitude, double radiusInKm) {
//        Point point = new Point(latitude, longitude);
//        Circle circle = new Circle(point, radiusInKm);
//        return shopLocationRepository.findByPositionWithin(circle);
//    }
}
