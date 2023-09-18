package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;

import java.util.List;

public interface UserCardRepositoryCustom {

    List<Card> findAllByUserId(String userId);

}
