package com.finp.moic.util.database.repository;

import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.util.database.entity.ShopLocation;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.repository.CrudRepository;

public interface ShopLocationRepository extends CrudRepository<ShopLocation,String> {

    GeoResults<ShopLocation> findByPositionWithin(Circle circle);

}
