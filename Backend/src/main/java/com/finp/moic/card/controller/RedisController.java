package com.finp.moic.card.controller;

import com.finp.moic.util.config.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    private RedisTemplate redisTemplate;
    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService=redisService;
    }


    @PostMapping("/setValue")
    public void setValue(String key, String value) {
        redisService.setValue(key,value);
    }
    @PostMapping("/getValue")
    public String getValue(String key) {
        return redisService.getValue(key);
    }

}
