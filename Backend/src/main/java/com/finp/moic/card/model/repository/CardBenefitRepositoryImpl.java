package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.CardBenefit;
import com.finp.moic.card.model.entity.QCardBenefit;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CardBenefitRepositoryImpl implements CardBenefitRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CardBenefitRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<CardBenefit> findByCardName(String cardName) {

        QCardBenefit cardBenefit=QCardBenefit.cardBenefit;

        return queryFactory
                .select(cardBenefit)
                .from(cardBenefit)
                .where(cardBenefit.card.name.eq(cardName))
                .fetch();
    }
}
