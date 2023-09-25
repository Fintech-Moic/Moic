package com.finp.moic.util.database.service;

import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardRedisService {

    private final RedisTemplate<String, Object> mainRedis;

    @Autowired
    public CardRedisService(@Qualifier("MainRedis") RedisTemplate<String, Object> mainRedis) {
        this.mainRedis = mainRedis;
    }

    public String setUserCardKey(String userId){ return userId+".card"; }

    /**
     *  내 카드 저장
     *  **/
    public void setUserCard(String userId, String cardName){
        mainRedis.opsForList().rightPush(setUserCardKey(userId),cardName);
    }

    /**
     * 내 카드 목록 한번에 저장 (캐싱 데이터 날아가는 상황 발생 시 이용)
     *  **/
    public void setUserCardList(String userId, List<CardMineResponseDTO> userCardList){
        mainRedis.opsForList().rightPushAll(setUserCardKey(userId),userCardList);
    }

    /**
     * 내 카드 목록 조회
     * **/
    public List getUserCardList(String userId){
        return mainRedis.opsForList().range(setUserCardKey(userId),0,-1);
    }


}
