package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.dto.response.ShopDetailResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ShopRepositoryCustom {

    Boolean exist(String shopName);

    Optional<ShopDetailResponseDTO> findByNameAndLocation(String shopName, String shopLocation);

    String findShopNameByKeyword(String keyword);

    List<String> findAllShopNameByCategory(String category);

    List<String> findAllShopNameByMainCategoryAndSubCategory(String mainCategory, String subCategory);
}
