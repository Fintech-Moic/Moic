package com.finp.moic.giftCard.model.repository.querydsl;

import com.finp.moic.giftCard.model.dto.response.GiftcardListResponseDTO;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.shop.model.dto.response.GiftResponseDTO;

import java.util.List;
import java.util.Optional;

public interface GiftcardRepositoryCustom {
    List<GiftResponseDTO> findAllByShopName(String shopName);

    List<String> findAllShopNameByUserId(String userId);

    List<GiftcardListResponseDTO> findAllByUserId(String userId);

    /* 혜지 : 제대로 동작하지 않는 것으로 파악됨. 따라서 임시로 queryDSL로 이동! */
    Optional<Giftcard> findByImageUrl(String imageUrl);
}
