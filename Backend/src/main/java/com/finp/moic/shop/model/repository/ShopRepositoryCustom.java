package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;
import com.finp.moic.shop.model.entity.Shop;

import java.util.List;
import java.util.Optional;

public interface ShopRepositoryCustom {

    Boolean exist(String shopName);

    Optional<ShopDetailResponseDTO> findByNameAndLocation(String shopName, String shopLocation);

    String findShopNameByKeyword(String keyword);

    List<String> findAllShopNameByCategory(String category);

}
