package com.finp.moic.giftCard.model.repository;

import com.finp.moic.card.model.entity.QCardBenefit;
import com.finp.moic.card.model.entity.QUserCard;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.entity.QGiftcard;
import com.finp.moic.shop.model.dto.response.GiftResponseDTO;
import com.finp.moic.shop.model.dto.response.QGiftResponseDTO;
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
    public List<GiftResponseDTO> findAllByShopName(String shopName) {
        QGiftcard giftcard=QGiftcard.giftcard;

        return queryFactory
                .select(
                        new QGiftResponseDTO(
                                giftcard.productName,
                                giftcard.barcodeImage,
                                giftcard.barcodeNumber,
                                giftcard.dueDate
                        )
                )
                .from(giftcard)
                .where(giftcard.shopName.eq(shopName))
                .fetch();
    }

    @Override
    public List<String> findAllShopNameByUserId(String userId) {
        QGiftcard giftcard=QGiftcard.giftcard;

        return queryFactory
                .select(giftcard.shopName)
                .from(giftcard)
                .where(giftcard.user.id.eq(userId))
                .fetch();
    }
}
