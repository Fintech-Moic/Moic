package com.finp.moic.shop.controller;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;
import com.finp.moic.shop.service.ShopServiceImpl;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShopController {

    private final ShopServiceImpl shopServiceImpl;

    @Autowired
    public ShopController(ShopServiceImpl shopServiceImpl) {
        this.shopServiceImpl = shopServiceImpl;
    }

    @PostMapping("/test/location/java")
    public ResponseEntity<ResponseDTO> testJavaLocation(@RequestBody @Valid LocationRequestDTO locationRequestDTO){

        LocationResponseDTO response=shopServiceImpl.testJavaLocation(locationRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("JAVA 위경도 테스트")
                .data(response)
                .build());
    }

    @PostMapping("/test/location/rdb")
    public ResponseEntity<ResponseDTO> testRDBLocation(@RequestBody @Valid LocationRequestDTO locationRequestDTO){

        LocationResponseDTO response=shopServiceImpl.testRDBLocation(locationRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("RDB 위경도 테스트")
                .data(response)
                .build());
    }

    @PostMapping("/test/location/redis")
    public ResponseEntity<ResponseDTO> testRedisLocation(@RequestBody @Valid LocationRequestDTO locationRequestDTO){

        LocationResponseDTO response=shopServiceImpl.testRedisLocation(locationRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("Redis 위경도 테스트")
                .data(response)
                .build());
    }

    @GetMapping("/map/shops/detail")
    public ResponseEntity<ResponseDTO> detailShop(@RequestParam("shopName") String shopName,
                                                  @RequestParam("shopLocation") String shopLocation){

        /**
         * TO DO :: Get에서 DTO 생성 시 Validation할 방법 찾기
         **/
        ShopDetailRequestDTO shopDetailRequestDTO=new ShopDetailRequestDTO(shopName,shopLocation);

        ShopDetailResponseDTO response=shopServiceImpl.detailShop(shopDetailRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드혜택, 기프티콘 가맹점 상세 조회")
                .data(response)
                .build());
    }

    @GetMapping("/map/shops")
    public ResponseEntity<ResponseDTO> searchShop(@RequestParam("keyword") String keyword,
                                                  @RequestParam("latitude") double latitude,
                                                  @RequestParam("longitude") double longitude){

        /**
         * TO DO :: Get에서 DTO 생성 시 Validation할 방법 찾기
         **/
        ShopSearchRequestDTO shopSearchRequestDTO=new ShopSearchRequestDTO(keyword,latitude,longitude);

        ShopSearchResponseDTO response=shopServiceImpl.searchShop(shopSearchRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드혜택, 기프티콘 가맹점 조회")
                .data(response)
                .build());
    }

}
