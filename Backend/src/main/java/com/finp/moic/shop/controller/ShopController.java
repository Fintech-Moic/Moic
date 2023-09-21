package com.finp.moic.shop.controller;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;
import com.finp.moic.shop.service.ShopServiceImpl;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
