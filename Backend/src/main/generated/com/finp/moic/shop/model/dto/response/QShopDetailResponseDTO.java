package com.finp.moic.shop.model.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.finp.moic.shop.model.dto.response.QShopDetailResponseDTO is a Querydsl Projection type for ShopDetailResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QShopDetailResponseDTO extends ConstructorExpression<ShopDetailResponseDTO> {

    private static final long serialVersionUID = -540208544L;

    public QShopDetailResponseDTO(com.querydsl.core.types.Expression<String> category, com.querydsl.core.types.Expression<String> shopName, com.querydsl.core.types.Expression<String> shopLocation, com.querydsl.core.types.Expression<String> address, com.querydsl.core.types.Expression<Double> latitude, com.querydsl.core.types.Expression<Double> longitude, com.querydsl.core.types.Expression<? extends java.util.List<BenefitResponseDTO>> benefits, com.querydsl.core.types.Expression<? extends java.util.List<GiftResponseDTO>> gifts) {
        super(ShopDetailResponseDTO.class, new Class<?>[]{String.class, String.class, String.class, String.class, double.class, double.class, java.util.List.class, java.util.List.class}, category, shopName, shopLocation, address, latitude, longitude, benefits, gifts);
    }

}

