package com.finp.moic.shop.model.service;

import com.finp.moic.shop.model.dto.request.ShopCategoryRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;

import java.util.List;

public interface ShopService {

    ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO);

    List<ShopSearchResponseDTO> searchShop(ShopSearchRequestDTO shopSearchRequestDTO, String userId);

    List<ShopSearchResponseDTO> getShopListByCategory(ShopCategoryRequestDTO shopCategoryRequestDTO, String userId);
}
