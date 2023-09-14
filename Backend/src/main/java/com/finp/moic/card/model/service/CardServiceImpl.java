package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardDeleteRequestDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;
import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.UserCard;
import com.finp.moic.card.model.repository.CardRepository;
import com.finp.moic.card.model.repository.UserCardRepository;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CONFIRM :: Transaction
 **/
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final RedisService redisService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, UserRepository userRepository,
                           UserCardRepository userCardRepository, RedisService redisService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
        this.redisService = redisService;
    }

    @Override
    public void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId) {

        /*** Validation ***/
        
        /**
         * TO DO :: 중복된 카드에 대해 저장되지 않도록 처리 (삭제에서 예외 발생)
         * */
        
        Card card=cardRepository.findByName(cardRegistRequestDTO.getCardName())
                .orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_NOT_FOUND));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ExceptionEnum.USER_NOT_FOUND));

        /**
         * TO DO :: SOFT DELETE 확인해, 존재 시 회복하기
         * */

        /*** RDB Access ***/
        UserCard userCard=userCardRepository.save(UserCard.builder()
                .build());

        /*** Entity Builder ***/
        userCard=UserCard.builder()
                .userCardSeq(userCard.getUserCardSeq())
                .card(card)
                .user(user)
                .build();

        /*** RDB Access ***/
        userCardRepository.save(userCard);

    }

    @Override
    public List<CardResponseDTO> getCardList(String userId) {

        /*** Validation ***/

        /*** Entity Builder ***/

        /**
         * TO DO :: SOFT DELETE 확인해, 삭제된 데이터 가져오지 않기
         * */

        /*** RDB Access ***/
        List<Card> cardList=cardRepository.findAll();
        List<Card> cardNameList=cardRepository.findAllCardNameByUserId(userId);

        /*** DTO Builder ***/
        List<CardResponseDTO> dtoList=new ArrayList<>();
        for(Card card:cardList){
            boolean mine=false;
            for(Card userCard:cardNameList){
                if(userCard.getName().equals(card.getName())){
                    mine=true;
                    dtoList.add(
                            CardResponseDTO.builder()
                            .company(card.getCompany())
                            .type(card.getType())
                            .name(card.getName())
                            .cardImage(card.getCardImage())
                            .mine(true)
                            .build()
                    );
                    break;
                }
            }
            if(!mine) {
                dtoList.add(
                        CardResponseDTO.builder()
                                .company(card.getCompany())
                                .type(card.getType())
                                .name(card.getName())
                                .cardImage(card.getCardImage())
                                .mine(false)
                                .build()
                );
            }
        }

        return dtoList;
    }

    @Override
    public List<CardMineResponseDTO> getMyCardList(String userId) {

        /*** Validation ***/

        /*** Entity Builder ***/

        /**
         * TO DO :: SOFT DELETE 확인해, 삭제된 데이터 가져오지 않기
         * */

        /*** RDB Access ***/

        /**
         * TO DO :: 내 카드 목록 조회로 변경
         * */

        List<Card> cardList=cardRepository.findAll();

        /*** DTO Builder ***/
        List<CardMineResponseDTO> dtoList=new ArrayList<>();
        for(int i=0;i<cardList.size();i+=3){
                dtoList.add(
                        CardMineResponseDTO.builder()
                                .company(cardList.get(i).getCompany())
                                .type(cardList.get(i).getType())
                                .name(cardList.get(i).getName())
                                .cardImage(cardList.get(i).getCardImage())
                                .build()
                );
        }


        System.out.println("REDIS SAVE ");
        redisService.setUserCardList(userId,dtoList);

        System.out.println("REDIS RETURN ");
        List<CardMineResponseDTO> responseDTOS=redisService.getUserCardList(userId);

        System.out.println(responseDTOS.get(0));
        return dtoList;
    }

    @Override
    public void deleteCard(CardDeleteRequestDTO cardDeleteRequestDTO, String userId) {

        /*** Validation ***/
        UserCard userCard=userCardRepository.findByCardName(cardDeleteRequestDTO.getCardName())
                .orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_USER_NOT_FOUND));

        User user=userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ExceptionEnum.USER_NOT_FOUND));

        /*** RDB Access ***/
        userCardRepository.delete(userCard);

    }
}
