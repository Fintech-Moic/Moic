package com.finp.moic.card.model.repository.queryDSL;

import com.finp.moic.card.model.dto.response.CardResponseDTO;
import com.finp.moic.card.model.entity.Card;

import java.util.List;

public interface CardRepositoryCustom {

    /**
     * TO DO :: 필요한 칼럼만 받고, DTO로 리턴하도록 수정
     **/

    Boolean exist(String cardName);

    List<Card> search(String company, String type, String cardName);

    List<CardResponseDTO> findAllCard();
}
