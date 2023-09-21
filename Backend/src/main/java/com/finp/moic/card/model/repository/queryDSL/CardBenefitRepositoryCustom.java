package com.finp.moic.card.model.repository.queryDSL;

import com.finp.moic.card.model.entity.CardBenefit;

import java.util.List;

public interface CardBenefitRepositoryCustom {

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    List<CardBenefit> findByCardName(String cardName);

    List<CardBenefit> findAllByShopName(String shopName);
}
