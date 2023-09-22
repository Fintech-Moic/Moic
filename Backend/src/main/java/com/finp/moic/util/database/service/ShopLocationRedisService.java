package com.finp.moic.util.database.service;

import com.finp.moic.shop.model.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopLocationRedisService {

    private final RedisTemplate<String, Object> mainRedis;

    @Autowired
    public ShopLocationRedisService(@Qualifier("MainRedis") RedisTemplate<String, Object> mainRedis) {
        this.mainRedis = mainRedis;
    }

    /**
     * 가맹점별 위치와 정보 저장
     * **/
    public void setShopLocation(List<Shop> shopList){

    }

}
