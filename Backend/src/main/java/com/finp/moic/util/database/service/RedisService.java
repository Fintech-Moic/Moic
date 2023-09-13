package com.finp.moic.util.database.service;

import com.finp.moic.card.model.entity.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /***** [Auto Complete] *****/
    public String getValue(String key) {
        return stringValueOperations.get(key);
    }

    public void setValue(String key, String value) {
        stringValueOperations.set(key,value);
    }

    /***** [UserCards] *****/
    public String setUserCardKey(String userId){ return userId+".card"; }

    /**
     *  내 카드 저장
     *  **/
    public void setUserCard(String userId, String cardName){
        redisTemplate.opsForList().rightPush(setUserCardKey(userId),cardName);
    }

    /**
     * 내 카드 목록 한번에 저장 (캐싱 데이터 날아가는 상황 발생 시 이용)
     *  **/
    public void setUserCardList(String userId, List<UserCard> userCardList){
        redisTemplate.opsForList().rightPushAll(setUserCardKey(userId),userCardList);
    }

    /**
     * 내 카드 목록 조회
     * **/
    public List getUserCardList(String userId){
        return redisTemplate.opsForList().range(setUserCardKey(userId),0,-1);
    }

    /***** [User CardBenefits] *****/



    /***** [User GiftCards] *****/



}
