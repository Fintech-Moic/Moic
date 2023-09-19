package com.finp.moic.giftCard.model.repository;

import com.finp.moic.giftCard.model.entity.Giftcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GiftcardRepository extends JpaRepository<Giftcard,Long> {
}
