package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.QCard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CardRepositoryImpl implements CardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Card> search(String company, String type, String cardName) {

        QCard card=QCard.card;

        return queryFactory
                .select(card)
                .from(card)
                .where(
                        card.company.contains(company)
                                .and(card.type.contains(type))
                                .and(card.name.contains(cardName))
                )
                .fetch();
    }
}
