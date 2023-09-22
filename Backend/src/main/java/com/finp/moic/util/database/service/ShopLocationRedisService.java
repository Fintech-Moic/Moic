package com.finp.moic.util.database.service;

import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.util.database.entity.ShopLocationRedisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.domain.geo.GeoLocation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopLocationRedisService {

    private final RedisTemplate<String, Object> mainRedis;
    private final GeoOperations<String,Object> geoOperations;

    @Autowired
    public ShopLocationRedisService(@Qualifier("MainRedis") RedisTemplate<String, Object> mainRedis) {
        this.mainRedis = mainRedis;
        this.geoOperations = mainRedis.opsForGeo();
    }

    /**
     * 가맹점별 위치와 정보 저장 (Redis가 날아갔을 경우 대비)
     * **/
    public void setShopLocationList(List<Shop> shopList){
        System.out.println("SET SHOPLOCATIONLIST");
        for(Shop shop:shopList){
            geoOperations.add(
              shop.getName(), //KEY
              new Point(shop.getLongitude(),shop.getLatitude()),
              ShopLocationRedisDTO
                      .builder()
                      .mainCategory(shop.getMainCategory())
                      .category(shop.getCategory())
                      .location(shop.getLocation())
                      .address(shop.getAddress())
                      .guName(shop.getGuName())
                      .build()
            );
        }
        System.out.println("FINISH SAVING");
    }

    /**
     * 사용자 주변의 가맹점 정보 리턴
     * **/
    public List<ShopLocationRedisDTO> getShopLocationListNearByUser(String shopName, double latitude, double longitude,
                                                                    double radius){
        System.out.println("GET SHOPLOCATIONLIST NEARBYUSER");
        List<ShopLocationRedisDTO> shopLocationList=new ArrayList<>();

        GeoResults<RedisGeoCommands.GeoLocation<Object>> results= geoOperations.radius(shopName,new Circle(new Point(longitude,latitude),radius));
        System.out.println("RESULTS");
        System.out.println(results);
        for(Object object:results){
            shopLocationList.add((ShopLocationRedisDTO)object);
        }
        System.out.println("SHOPLOCATIONLIST");
        System.out.println(shopLocationList);

        return shopLocationList;
    }

}
