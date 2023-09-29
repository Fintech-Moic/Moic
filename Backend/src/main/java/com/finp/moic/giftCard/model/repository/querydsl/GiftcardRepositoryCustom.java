package com.finp.moic.giftCard.model.repository.querydsl;

import com.finp.moic.giftCard.model.dto.response.GiftcardListResponseDTO;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.shop.model.dto.response.GiftResponseDTO;

import java.util.List;

public interface GiftcardRepositoryCustom {
    List<GiftResponseDTO> findAllByShopName(String shopName);

    List<String> findAllShopNameByUserId(String userId);

    List<GiftcardListResponseDTO> findAllByUserId(String userId);
}
