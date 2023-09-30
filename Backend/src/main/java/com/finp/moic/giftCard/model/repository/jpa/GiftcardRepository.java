package com.finp.moic.giftCard.model.repository.jpa;

import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.repository.querydsl.GiftcardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GiftcardRepository extends JpaRepository<Giftcard,Long>, GiftcardRepositoryCustom {

    /* 혜지 : 제대로 동작하지 않는 것으로 파악됨. 따라서 임시로 queryDSL로 이동! */

    /**
     * CONFIRM :: 이제 UUID로 구성된 seq을 넘겨주니까 그것으로 FIND하는 것을 어떨까요?
     **/
    Optional<Giftcard> findByImageUrl(String url);
}
