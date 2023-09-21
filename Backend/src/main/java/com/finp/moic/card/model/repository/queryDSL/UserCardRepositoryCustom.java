package com.finp.moic.card.model.repository.queryDSL;

import com.finp.moic.card.model.entity.Card;

import java.util.List;

public interface UserCardRepositoryCustom {

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/
    List<Card> findAllByUserId(String userId);

}
