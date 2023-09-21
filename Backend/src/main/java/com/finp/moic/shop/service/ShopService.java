package com.finp.moic.shop.service;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;

import java.util.List;

public interface ShopService {
    LocationResponseDTO testJavaLocation(LocationRequestDTO locationRequestDTO);

    LocationResponseDTO testRDBLocation(LocationRequestDTO locationRequestDTO);

    LocationResponseDTO testRedisLocation(LocationRequestDTO locationRequestDTO);

    ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO);

    List<ShopSearchResponseDTO> searchShop(ShopSearchRequestDTO shopSearchRequestDTO);
}
