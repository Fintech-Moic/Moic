package com.finp.moic.shop.controller;

import com.finp.moic.shop.model.dto.request.ShopCategoryRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;
import com.finp.moic.shop.model.service.ShopService;
import com.finp.moic.util.database.entity.ShopLocationRedisDTO;
import com.finp.moic.util.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/map/category")
    public ResponseEntity<ResponseDTO> getShopListByCategory(@RequestParam("category") String category,
                                                             @RequestParam("latitude") double latitude,
                                                             @RequestParam("longitude") double longitude,
                                                             @RequestParam("userId") String userId){

        ShopCategoryRequestDTO shopCategoryRequestDTO=new ShopCategoryRequestDTO(category,latitude,longitude);
        List<ShopSearchResponseDTO> dto= shopService.getShopListByCategory(shopCategoryRequestDTO,userId);
        HashMap<String,Object> response=new HashMap<>();
        response.put("shopList",dto);

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

        ShopDetailResponseDTO response= shopService.detailShop(shopDetailRequestDTO);

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

        List<ShopSearchResponseDTO> dto= shopService.searchShop(shopSearchRequestDTO);
        HashMap<String, Object> response=new HashMap<>();
        response.put("shopList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드혜택, 기프티콘 가맹점 조회")
                .data(response)
                .build());
    }

}
