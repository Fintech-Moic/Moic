package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop,Long> {
}
