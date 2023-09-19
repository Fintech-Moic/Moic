package com.finp.moic.shop.service;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;

public interface ShopService {
    LocationResponseDTO testJavaLocation(LocationRequestDTO locationRequestDTO);

    LocationResponseDTO testRDBLocation(LocationRequestDTO locationRequestDTO);

    LocationResponseDTO testRedisLocation(LocationRequestDTO locationRequestDTO);
}
