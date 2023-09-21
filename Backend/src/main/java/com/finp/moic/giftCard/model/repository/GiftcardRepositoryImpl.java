package com.finp.moic.giftCard.model.repository;

import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.entity.QGiftcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GiftcardRepositoryImpl implements GiftcardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public GiftcardRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    @Override
    public List<Giftcard> findAllByShopName(String shopName) {

        QGiftcard giftcard=QGiftcard.giftcard;

        return queryFactory
                .select(giftcard)
                .from(giftcard)
                .where(giftcard.shopName.eq(shopName))
                .fetch();
    }
}
