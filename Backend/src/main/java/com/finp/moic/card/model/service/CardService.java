package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardRegistResponseDTO;

public interface CardService {

    public CardRegistResponseDTO registCard(CardRegistRequestDTO cardRegistRequestDTO);
}
