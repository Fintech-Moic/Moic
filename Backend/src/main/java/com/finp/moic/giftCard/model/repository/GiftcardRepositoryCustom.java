package com.finp.moic.giftCard.model.repository;

import com.finp.moic.giftCard.model.entity.Giftcard;

import java.util.List;

public interface GiftcardRepositoryCustom {
    List<Giftcard> findAllByShopName(String shopName);
}
