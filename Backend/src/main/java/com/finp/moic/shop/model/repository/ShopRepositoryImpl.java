package com.finp.moic.shop.model.repository;

import com.finp.moic.shop.model.entity.QShop;
import com.finp.moic.shop.model.entity.Shop;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShopRepositoryImpl implements ShopRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ShopRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    @Override
    public Shop findShopDetail(String shopName, String shopLocation) {

        QShop shop=QShop.shop;

        return queryFactory
                .select(shop)
                .from(shop)
                .where(shop.name.eq(shopName)
                        .and(shop.location.eq(shopLocation))
                )
                .fetchOne();
    }

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    @Override
    public List<Shop> findByKeyword(String keyword) {

        QShop shop=QShop.shop;

        return queryFactory
                .select(shop)
                .from(shop)
                .where(shop.name.contains(keyword))
                .fetch();
    }
}