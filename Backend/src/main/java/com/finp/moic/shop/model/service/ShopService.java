package com.finp.moic.shop.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;
import com.finp.moic.util.database.entity.ShopLocationRedisDTO;

import java.util.List;

public interface ShopService {
    ShopLocationRedisDTO testRedisLocation(/*LocationRequestDTO locationRequestDTO*/) throws JsonProcessingException;

    ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO);

    List<ShopSearchResponseDTO> searchShop(ShopSearchRequestDTO shopSearchRequestDTO);
}
