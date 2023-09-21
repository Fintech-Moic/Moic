package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.entity.Shop;

import java.util.List;

public interface ShopRepositoryCustom {
    Shop findShopDetail(String shopName, String shopLocation);

    List<Shop> findByKeyword(String keyword);
}
