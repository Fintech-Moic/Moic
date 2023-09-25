package com.finp.moic.util.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

public class UserBenefitShopRedisService {

    private final RedisTemplate<String, Object> mainRedis;

    @Autowired
    public UserBenefitShopRedisService(@Qualifier("MainRedis") RedisTemplate<String, Object> mainRedis) {
        this.mainRedis = mainRedis;
    }

}
