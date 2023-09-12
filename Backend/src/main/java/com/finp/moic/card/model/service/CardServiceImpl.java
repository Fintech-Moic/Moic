package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;
import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.UserCard;
import com.finp.moic.card.model.repository.CardRepository;
import com.finp.moic.card.model.repository.UserCardRepository;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository,
                           UserCardRepository userCardRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
    }

    @Override
    public void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId) {

        System.out.println("CARD REGIST :: SERVICE ARRIVAL");

        /*** Validation ***/
        Card card=cardRepository.findByName(cardRegistRequestDTO.getCardName()).orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_NOT_FOUND));
        User user=userRepository.findById(userId).orElseThrow(()->new NotFoundException(ExceptionEnum.USER_NOT_FOUND));

        /*** RDB Access ***/
        UserCard userCard=userCardRepository.save(UserCard.builder()
                .build());
        System.out.println("CREATE USERCARD :: "+userCard.getUserCardSeq());

        userCard=UserCard.builder()
                .userCardSeq(userCard.getUserCardSeq())
                .card(card)
                .user(user)
                .build();
        System.out.println("USERCARD :: "+userCard);

        userCardRepository.save(userCard);
    }

    @Override
    public List<CardResponseDTO> getCardList(String userId) {

        /*** Validation ***/

        /*** Entity Builder ***/

        /*** RDB Access ***/


        /*** DTO Builder ***/



        return null;
    }


}
