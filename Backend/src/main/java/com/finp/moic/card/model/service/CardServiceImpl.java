package com.finp.moic.card.model.service;

import com.finp.moic.card.model.dto.request.CardDeleteRequestDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.request.CardSearchRequestDTO;
import com.finp.moic.card.model.dto.response.*;
import com.finp.moic.card.model.entity.Card;
import com.finp.moic.card.model.entity.CardBenefit;
import com.finp.moic.card.model.entity.UserCard;
import com.finp.moic.card.model.repository.CardBenefitRepository;
import com.finp.moic.card.model.repository.CardRepository;
import com.finp.moic.card.model.repository.UserCardRepository;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.database.service.RedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.AlreadyExistException;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * CONFIRM :: Transaction
 **/
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardBenefitRepository cardBenefitRepository;
    private final UserRepository userRepository;
    private final UserCardRepository userCardRepository;
    private final RedisService redisService;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, CardBenefitRepository cardBenefitRepository,
                           UserRepository userRepository, UserCardRepository userCardRepository,
                           RedisService redisService) {
        this.cardRepository = cardRepository;
        this.cardBenefitRepository = cardBenefitRepository;
        this.userRepository = userRepository;
        this.userCardRepository = userCardRepository;
        this.redisService = redisService;
    }

    @Override
    public void registCard(CardRegistRequestDTO cardRegistRequestDTO, String userId) {

        /**
         * TO DO :: SOFT DELETE 확인해, 존재 시 회복하기
         * */

        /*** Validation ***/
        Card card=cardRepository.findByName(cardRegistRequestDTO.getCardName())
                .orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_NOT_FOUND));
        User user=userRepository.findById(userId)
                .orElseThrow(()->new NotFoundException(ExceptionEnum.USER_NOT_FOUND));

        /* 혜지 : 한 사용자에 대해 중복된 카드 등록 불가 */
        List<Card> cardList=userCardRepository.findAllByUserId(userId);
        for(Card userCard:cardList){
            if(card.getName().equals(userCard.getName())){
                throw new AlreadyExistException(ExceptionEnum.CARD_REGIST_DUPLICATE);
            }
        }

        /*** RDB Access ***/
        /* 혜지 : userCardSeq 등의 기본 데이터셋 저장 */
        UserCard userCard=userCardRepository.save(UserCard.builder()
                .build());

        /*** Entity Builder ***/
        userCard=UserCard.builder()
                .userCardSeq(userCard.getUserCardSeq())
                .card(card)
                .user(user)
                .build();

        /*** RDB Access ***/
        /* 혜지 : userCard FK 저장 */
        userCardRepository.save(userCard);

    }

    @Override
    public CardAllReponseDTO getCardList(String userId) {

        /**
         * TO DO :: SOFT DELETE 확인해, 삭제된 데이터 가져오지 않기
         * */

        /*** RDB Access ***/
        List<String> companyList=cardRepository.findAllCompany();
        List<String> typeList=cardRepository.findAllType();

        List<Card> allCardList=cardRepository.findAll();
        List<Card> myCardList=userCardRepository.findAllByUserId(userId);

        /*** DTO Builder ***/
        List<CardResponseDTO> cardDTOList=new ArrayList<>();
        for(Card card:allCardList){
            boolean mine=false;
            for(Card userCard:myCardList){
                if(card.getName().equals(userCard.getName())){
                    mine=true;
                    cardDTOList.add(
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
                cardDTOList.add(
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

        Collections.sort(companyList);
        Collections.sort(typeList);

        CardAllReponseDTO dto=CardAllReponseDTO.builder()
                .companyList(companyList)
                .typeList(typeList)
                .cardList(cardDTOList)
                .build();

        return dto;
    }

    @Override
    public List<CardMineResponseDTO> getMyCardList(String userId) {

        /**
         * TO DO :: SOFT DELETE 확인해, 삭제된 데이터 가져오지 않기
         * */

        /*** RDB Access ***/
        List<Card> cardList=userCardRepository.findAllByUserId(userId);

        /*** DTO Builder ***/
        List<CardMineResponseDTO> dtoList=new ArrayList<>();
        for(Card card:cardList){
                dtoList.add(
                        CardMineResponseDTO.builder()
                                .company(card.getCompany())
                                .type(card.getType())
                                .name(card.getName())
                                .cardImage(card.getCardImage())
                                .build()
                );
        }

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

    public CardDetailResponseDTO detailCard(String cardName) {

        /*** Validation ***/
        Card card=cardRepository.findByName(cardName)
                .orElseThrow(()->new NotFoundException(ExceptionEnum.CARD_NOT_FOUND));

        /*** RDB Access ***/
        List<CardBenefit> cardBenefitList=cardBenefitRepository.findByCardName(cardName);

        /*** DTO Builder ***/
        List<CardBenefitResponseDTO> cardBenefitDTOList=new ArrayList<>();
        for(CardBenefit cardBenefit: cardBenefitList){
            cardBenefitDTOList.add(
                    CardBenefitResponseDTO.builder()
                            .category(cardBenefit.getCategory())
                            .shopName(cardBenefit.getShopName())
                            .content(cardBenefit.getContent())
                            .discount(cardBenefit.getDiscount())
                            .point(cardBenefit.getPoint())
                            .cashBack(cardBenefit.getCashback())
                            .build());
        }

        CardDetailResponseDTO dto=CardDetailResponseDTO.builder()
                .company(card.getCompany())
                .type(card.getType())
                .name(card.getName())
                .cardImage(card.getCardImage())
                .cardBenefit(cardBenefitDTOList)
                .build();

        return dto;
    }

    @Override
    public CardSearchResponseDTO searchCard(CardSearchRequestDTO cardSearchRequestDTO, String userId) {

        String company=cardSearchRequestDTO.getCompany();
        String type=cardSearchRequestDTO.getType();
        String cardName=cardSearchRequestDTO.getCardName();

        /*** RDB Access ***/
        List<Card> cardList=cardRepository.search(company,type,cardName);

        /*** DTO Builder ***/
        HashSet<String> companySet=new HashSet<>();
        HashSet<String> typeSet=new HashSet<>();

        List<CardResponseDTO> cardDTOList=new ArrayList<>();
        List<Card> myCardList=userCardRepository.findAllByUserId(userId);

        for(Card card:cardList){
            if(!companySet.contains(card.getCompany())) companySet.add(card.getCompany());
            if(!typeSet.contains(card.getType())) typeSet.add(card.getType());

            boolean mine=false;
            for(Card userCard:myCardList){
                if(card.getName().equals(userCard.getName())){
                    mine=true;
                    cardDTOList.add(
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
                cardDTOList.add(
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

        ArrayList<String> companyList=new ArrayList<>(companySet);
        ArrayList<String> typeList=new ArrayList<>(typeSet);

        Collections.sort(companyList);
        Collections.sort(typeList);

        CardSearchResponseDTO dto=CardSearchResponseDTO.builder()
                .companyList(companyList)
                .typeList(typeList)
                .cardList(cardDTOList)
                .build();

        return dto;
    }
}
