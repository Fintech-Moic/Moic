package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.CardBenefit;

import java.util.List;

public interface CardBenefitRepositoryCustom {
    List<CardBenefit> findByCardName(String cardName);
}
