package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardDeleteRequestDTO;
import com.finp.moic.card.model.dto.request.CardDetailRequestDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.request.CardSearchRequestDTO;
import com.finp.moic.card.model.dto.response.*;

import java.util.List;

public interface CardService {

    void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId);

    List<CardMineResponseDTO> getMyCardList(String userId);

    CardAllReponseDTO getCardList(String userId);

    void deleteCard(CardDeleteRequestDTO cardDeleteRequestDTO, String userId);

    CardDetailResponseDTO detailCard(CardDetailRequestDTO cardDetailRequestDTO);

    CardSearchResponseDTO searchCard(CardSearchRequestDTO cardSearchRequestDTO, String userId);
}
