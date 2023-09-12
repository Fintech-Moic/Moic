package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardRegistResponseDTO;
import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.repository.CardRepository;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    /* 혜지 : 인덱스 테스트 코드. 추후 삭제 예정 */
    public CardRegistResponseDTO registCard(CardRegistRequestDTO cardRegistRequestDTO) {

        /*** Entity Builder ***/
        Card card=Card
                .builder()
                .company(cardRegistRequestDTO.getCompany())
                .type(cardRegistRequestDTO.getType())
                .name(cardRegistRequestDTO.getName())
                .cardImage(cardRegistRequestDTO.getCardImage())
                .build();

        /*** RDB Access ***/
        cardRepository.save(card);

        cardRepository.findByName(card.getName()).orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_LOAD_ERROR));

        /*** DTO Builder ***/
        CardRegistResponseDTO dto=CardRegistResponseDTO
                .builder()
                .cardSeq(card.getCardSeq())
                .company(card.getCompany())
                .type(card.getType())
                .name(card.getName())
                .cardImage(card.getCardImage())
                .build();

        return dto;
    }
}
