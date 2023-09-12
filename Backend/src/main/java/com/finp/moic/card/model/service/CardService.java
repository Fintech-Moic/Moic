package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;

import java.util.List;

public interface CardService {

    public void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId);

    public List<CardResponseDTO> getCardList(String userId);

}
