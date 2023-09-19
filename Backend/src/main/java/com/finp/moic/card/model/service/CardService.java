package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardDeleteRequestDTO;
import com.finp.moic.card.model.dto.request.CardDetailRequestDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardDetailResponseDTO;
import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;

import java.util.List;

public interface CardService {

    void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId);

    List<CardMineResponseDTO> getMyCardList(String userId);

    List<CardResponseDTO> getCardList(String userId);

    void deleteCard(CardDeleteRequestDTO cardDeleteRequestDTO, String userId);

    CardDetailResponseDTO detailCard(CardDetailRequestDTO cardDetailRequestDTO);
}
