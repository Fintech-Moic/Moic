package com.finp.moic.shop.controller;

import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;
import com.finp.moic.shop.model.service.ShopService;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Validated
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/map/shops/detail")
    public ResponseEntity<ResponseDTO> detailShop(@RequestParam("shopName") @NotBlank String shopName,
                                                  @RequestParam("shopLocation") @NotNull String shopLocation){

        ShopDetailResponseDTO response= shopService.detailShop(shopName, shopLocation);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드혜택, 기프티콘 가맹점 상세 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 어노테이션 활용
     **/
    @GetMapping("/map/shops")
    public ResponseEntity<ResponseDTO> searchShop(@RequestParam("keyword") @NotNull String keyword,
                                                  @RequestParam("latitude") @Positive double latitude,
                                                  @RequestParam("longitude") @Positive double longitude,
                                                  @RequestParam("userId") String userId){

        List<ShopSearchResponseDTO> dto= shopService.searchShop(keyword,latitude,longitude, userId);
        HashMap<String, Object> response=new HashMap<>();
        response.put("shopList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드혜택, 기프티콘 가맹점 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 어노테이션 활용
     **/
    @GetMapping("/map/category")
    public ResponseEntity<ResponseDTO> getShopListByCategory(@RequestParam("category") @NotBlank String category,
                                                             @RequestParam("latitude") @Positive double latitude,
                                                             @RequestParam("longitude") @Positive double longitude,
                                                             @RequestParam("userId") String userId){

        List<ShopSearchResponseDTO> dto= shopService.getShopListByCategory(category,latitude,longitude,userId);
        HashMap<String,Object> response=new HashMap<>();
        response.put("shopList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("Redis 위경도 테스트")
                .data(response)
                .build());
    }

}
