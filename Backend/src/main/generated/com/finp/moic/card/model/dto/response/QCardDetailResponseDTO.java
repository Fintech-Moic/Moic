package com.finp.moic.card.model.dto.response;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.finp.moic.card.model.dto.response.QCardDetailResponseDTO is a Querydsl Projection type for CardDetailResponseDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QCardDetailResponseDTO extends ConstructorExpression<CardDetailResponseDTO> {

    private static final long serialVersionUID = 1266429612L;

    public QCardDetailResponseDTO(com.querydsl.core.types.Expression<String> id, com.querydsl.core.types.Expression<String> company, com.querydsl.core.types.Expression<String> type, com.querydsl.core.types.Expression<String> name, com.querydsl.core.types.Expression<String> cardImage, com.querydsl.core.types.Expression<? extends java.util.List<CardBenefitResponseDTO>> cardBenefit) {
        super(CardDetailResponseDTO.class, new Class<?>[]{String.class, String.class, String.class, String.class, String.class, java.util.List.class}, id, company, type, name, cardImage, cardBenefit);
    }

}

