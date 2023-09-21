package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long> {

    Shop findByNameAndLocation(@Param("name") String shopName, @Param("location") String shopLocation);
}
