package com.finp.moic.util.database.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import com.finp.moic.card.model.entity.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisService {

    /* 성재 : Redis 서버 2개 분리 */

    /**
     * 모든 데이터가 저장되있는 Redis 접근 : redisTemplate1
     */
    private final RedisTemplate<String, Object> redisTemplate1;

    /**
     * Refresh Token만 저장되있는 Redis 접근 : redisTemplate2
     */
    private final RedisTemplate<String, Object> redisTemplate2; //

    @Autowired
    public RedisService(@Qualifier("redisTemplate1") RedisTemplate redisTemplate1,
                                 @Qualifier("redisTemplate2") RedisTemplate redisTemplate2) {
        this.redisTemplate1=redisTemplate1;
        this.redisTemplate2=redisTemplate2;
    }


    /***** [Auto Complete] *****/

    /* 성재 : Auto Complete 작성 부분 남겨두기 */

    /***** [UserCards] *****/
    public String setUserCardKey(String userId){ return userId+".card"; }

    /**
     *  내 카드 저장
     *  **/
    public void setUserCard(String userId, String cardName){
        redisTemplate1.opsForList().rightPush(setUserCardKey(userId),cardName);
    }

    /**
     * 내 카드 목록 한번에 저장 (캐싱 데이터 날아가는 상황 발생 시 이용)
     *  **/
    public void setUserCardList(String userId, List<CardMineResponseDTO> userCardList){
        redisTemplate1.opsForList().rightPushAll(setUserCardKey(userId),userCardList);
    }

    /**
     * 내 카드 목록 조회
     * **/
    public List getUserCardList(String userId){
        return redisTemplate1.opsForList().range(setUserCardKey(userId),0,-1);
    }

    /***** [User CardBenefits] *****/



    /***** [User GiftCards] *****/



}
