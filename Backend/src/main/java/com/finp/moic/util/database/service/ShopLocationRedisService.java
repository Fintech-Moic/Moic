package com.finp.moic.util.database.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;
import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.util.database.entity.ShopLocationRedisDTO;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.DeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.geo.*;
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
     * 가맹점별 위치와 정보 저장 (Redis가 날아갔을 경우 대비 -> 되도록 쓰지 말기)
     * **/
    public void setDummy(){
        geoOperations.add(
                "key",new Point(127.0396597,37.5013068),"dummy"
        );
        System.out.println(geoOperations.position("key","dummy"));
    }

    public void setShopLocationList(List<Shop> shopList) {

        /** Redis Access **/
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            for (Shop shop : shopList) {
                geoOperations.add(
                        shop.getName(), //KEY
                        new Point(shop.getLongitude(), shop.getLatitude()), //POINT
                        objectMapper.writeValueAsString( //MEMBER
                                ShopLocationRedisDTO
                                        .builder()
                                        .mainCategory(shop.getMainCategory())
                                        .category(shop.getCategory())
                                        .location(shop.getLocation())
                                        .address(shop.getAddress())
                                        .guName(shop.getGuName())
                                        /* 혜지 Redis의 Point는 소수점 아래 여섯자리까지 저장한다. 따라서 우리의 데이터 이용 */
                                        .latitude(shop.getLatitude())
                                        .longitude(shop.getLongitude())
                                        .build()
                        )
                );
            }
        }catch (Exception e){
            throw new DeniedException(ExceptionEnum.SHOP_SAVE_ERROR);
        }
    }

    public List<ShopSearchResponseDTO> searchShopListNearByUser(String shopName, double latitude, double longitude) {

        List<ShopSearchResponseDTO> dto=new ArrayList<>();

        /** Redis Access **/
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results= geoOperations.radius(shopName,
                new Circle(new Point(longitude,latitude),new Distance(1, RedisGeoCommands.DistanceUnit.KILOMETERS)));

        /** DTO Builder **/
        for (GeoResult<RedisGeoCommands.GeoLocation<Object>> geoResult : results) {
            RedisGeoCommands.GeoLocation<Object> location = geoResult.getContent();
            String json=(String) location.getName();

            try {
                ShopLocationRedisDTO redisDTO = ShopLocationRedisDTO.fromJson(json);
                ShopSearchResponseDTO searchDTO=ShopSearchResponseDTO.builder()
                        .category(redisDTO.getCategory())
                        .shopName(shopName)
                        .shopLocation(redisDTO.getLocation())
                        .address(redisDTO.getAddress())
                        .latitude(redisDTO.getLatitude())
                        .longitude(redisDTO.getLongitude())
                        .build();
                dto.add(searchDTO);
            } catch (Exception e) {
                throw new DeniedException(ExceptionEnum.SHOP_SEARCH_ERROR);
            }
        }

        return dto;
    }
}
