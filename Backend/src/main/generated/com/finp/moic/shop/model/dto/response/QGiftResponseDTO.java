package com.finp.moic.shop.model.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.finp.moic.shop.model.dto.response.QGiftResponseDTO is a Querydsl Projection type for GiftResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QGiftResponseDTO extends ConstructorExpression<GiftResponseDTO> {

    private static final long serialVersionUID = -421412041L;

    public QGiftResponseDTO(com.querydsl.core.types.Expression<String> productName, com.querydsl.core.types.Expression<String> barcodeImage, com.querydsl.core.types.Expression<Long> barcodeNumber, com.querydsl.core.types.Expression<java.time.LocalDateTime> dueDate) {
        super(GiftResponseDTO.class, new Class<?>[]{String.class, String.class, long.class, java.time.LocalDateTime.class}, productName, barcodeImage, barcodeNumber, dueDate);
    }

}

