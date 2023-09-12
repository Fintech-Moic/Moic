package com.finp.moic.util.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    /* 성재 : autoComplete는 StringRedisTemplate 사용, 그 이외는 RedisTemplate(필요에 맞게) */
    private final RedisTemplate<String,Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private ValueOperations<String, String> stringValueOperations;

    @Autowired
    public RedisService(RedisTemplate redisTemplate, StringRedisTemplate stringRedisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.stringValueOperations = this.stringRedisTemplate.opsForValue();
    }

    public String getValue(String key) {
        return stringValueOperations.get(key);
    }

    public void setValue(String key, String value) {
        stringValueOperations.set(key,value);
    }


}
