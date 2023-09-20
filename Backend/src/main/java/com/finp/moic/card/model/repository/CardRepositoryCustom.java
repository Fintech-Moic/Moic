package com.finp.moic.card.model.repository;

import com.finp.moic.card.model.entity.Card;

import java.util.List;

public interface CardRepositoryCustom {
    List<Card> search(String company, String type, String cardName);
}
