package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.QCard;
import com.finp.moic.card.model.entity.QUserCard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserCardRepositoryImpl implements UserCardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public UserCardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Card> findAllByUserId(String userId) {

        QUserCard userCard=QUserCard.userCard;
        QCard card=QCard.card;

        return queryFactory
                .select(card)
                .from(userCard)
                .where(userCard.user.id.eq(userId))
                .fetch();
    }

}
