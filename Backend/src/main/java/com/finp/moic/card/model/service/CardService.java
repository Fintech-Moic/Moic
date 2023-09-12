package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardRegistResponseDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;

import java.util.List;

public interface CardService {

    public CardRegistResponseDTO registCard(CardRegistRequestDTO cardRegistRequestDTO);

    public List<CardResponseDTO> getCardList(String token);
}
