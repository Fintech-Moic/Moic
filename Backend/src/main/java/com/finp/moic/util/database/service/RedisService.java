package com.finp.moic.util.database.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import com.finp.moic.card.model.entity.UserCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    /* 성재 : Redis 서버 3개 분리 */

    /**
     * 모든 데이터가 저장되있는 Redis 접근 : MainRedis
     */
    private final RedisTemplate<String, Object> mainRedis;

    /**
     * Refresh Token만 저장되있는 Redis 접근 : SubRedis
     */
    private final RedisTemplate<String, String> securityRedis; //

    private final RedisTemplate<String, String> autoRedis;

    @Autowired
    public RedisService(@Qualifier("MainRedis") RedisTemplate<String, Object> mainRedis,
                        @Qualifier("SecurityRedis") RedisTemplate<String, String> securityRedis,
                        @Qualifier("AutoRedis") RedisTemplate<String, String> autoRedis) {
        this.mainRedis = mainRedis;
        this.securityRedis = securityRedis;
        this.autoRedis = autoRedis;
    }

    /***** [Auto Complete] *****/

    /* 성재 : Auto Complete 작성 부분 남겨두기 */

    /***** [UserCards] *****/
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

    /***** [User CardBenefits] *****/



    /***** [User GiftCards] *****/

    /***** [Refresh Token] *****/
    public void setRefreshToken(String userId, String refreshToken){
        securityRedis.opsForValue().set(refreshToken,userId);
        //일단 60초
        securityRedis.expire(refreshToken,60L, TimeUnit.SECONDS);
    }

    public String getRefreshToken(String refreshToken){
        return securityRedis.opsForValue().get(refreshToken);
    }

}
