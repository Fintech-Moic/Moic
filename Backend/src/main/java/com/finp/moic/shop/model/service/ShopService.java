package com.finp.moic.shop.model.service;

import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopSearchResponseDTO;

import java.util.List;

public interface ShopService {

    ShopDetailResponseDTO detailShop(String shopName, String ShopLocation);

    List<ShopSearchResponseDTO> searchShop(String keyword, double latitude, double longitude, String userId);

    List<ShopSearchResponseDTO> getShopListByCategory(String category, double latitude, double longitude, String userId);
}
