package com.finp.moic.giftCard.model.repository.querydsl;

import com.finp.moic.giftCard.model.dto.response.GiftcardListResponseDTO;
import com.finp.moic.giftCard.model.dto.response.QGiftcardListResponseDTO;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.entity.QGiftcard;
import com.finp.moic.shop.model.dto.response.GiftResponseDTO;
import com.finp.moic.shop.model.dto.response.QGiftResponseDTO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
                                giftcard.imageUrl,
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

    public List<GiftcardListResponseDTO> findAllByUserId(String userId) {
        QGiftcard giftcard=QGiftcard.giftcard;

        return queryFactory
                .select(
                        new QGiftcardListResponseDTO(
                                /* 혜지 : UUID 추가. 확인 후 주석 삭제 요망! */
                                giftcard.giftcardSeq,
                                giftcard.imageUrl,
                                giftcard.dueDate
                        )
                )
                .from(giftcard)
                .where(giftcard.user.id.eq(userId))
                .fetch();
    }

    /* 혜지 : 제대로 동작하지 않는 것으로 파악됨. 따라서 임시로 queryDSL로 이동! */
    @Override
    public Optional<Giftcard> findByImageUrl(String imageUrl) {
        QGiftcard giftcard=QGiftcard.giftcard;

        return Optional.ofNullable(queryFactory
                .selectFrom(giftcard)
                .where(giftcard.imageUrl.eq(imageUrl))
                .fetchOne());
    }
}
