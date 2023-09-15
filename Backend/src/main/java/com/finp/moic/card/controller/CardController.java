package com.finp.moic.card.controller;

import com.finp.moic.card.model.dto.request.CardDeleteRequestDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.dto.response.CardMineResponseDTO;
import com.finp.moic.card.model.dto.response.CardResponseDTO;
import com.finp.moic.card.model.service.CardServiceImpl;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardServiceImpl cardServiceImpl;

    @Autowired
    public CardController(CardServiceImpl cardServiceImpl) {
        this.cardServiceImpl = cardServiceImpl;
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registCard(@RequestBody @Valid CardRegistRequestDTO cardRegistRequestDTO/*,
                                                  @AuthenticationPrincipal UserAuthentication userAuthentication*/){

        cardServiceImpl.registCard(cardRegistRequestDTO, /*userAuthentication.getId()*/cardRegistRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 등록이 완료되었습니다.")
                        .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/ @RequestBody String userId){

        List<CardResponseDTO> dto=cardServiceImpl.getCardList(/*userAuthentication.getId()*/userId);
        HashMap<String, List<CardResponseDTO>> response=new HashMap<>();
        response.put("cardList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("전체 카드 목록 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/mycards")
    public ResponseEntity<ResponseDTO> getMyCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/ @RequestBody String userId){

        List<CardMineResponseDTO> dto=cardServiceImpl.getMyCardList(/*userAuthentication.getId()*/userId);
        HashMap<String, List<CardMineResponseDTO>> response=new HashMap<>();
        response.put("cardList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드 목록 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteCard(@RequestBody @Valid CardDeleteRequestDTO cardDeleteRequestDTO
                                                /*@AuthenticationPrincipal UserAuthentication userAuthentication*/){

        cardServiceImpl.deleteCard(cardDeleteRequestDTO,/*userAuthentication.getId()*/cardDeleteRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 삭제가 완료되었습니다.")
                        .build());
    }

}