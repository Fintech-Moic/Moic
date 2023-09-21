package com.finp.moic.card.controller;

import com.finp.moic.card.model.dto.request.*;
import com.finp.moic.card.model.dto.response.*;
import com.finp.moic.card.model.service.CardService;
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

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registCard(@RequestBody @Valid CardRegistRequestDTO cardRegistRequestDTO/*,
                                                  @AuthenticationPrincipal UserAuthentication userAuthentication*/){

        cardService.registCard(cardRegistRequestDTO, /*userAuthentication.getId()*/cardRegistRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 등록이 완료되었습니다.")
                        .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/@RequestParam("userId") String userId){

        CardAllReponseDTO response= cardService.getCardList(/*userAuthentication.getId()*/userId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("전체 카드 목록 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/mycards")
    public ResponseEntity<ResponseDTO> getMyCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/@RequestParam("userId") String userId) {

        List<CardMineResponseDTO> dto= cardService.getMyCardList(/*userAuthentication.getId()*/userId);
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

        cardService.deleteCard(cardDeleteRequestDTO,/*userAuthentication.getId()*/cardDeleteRequestDTO.getUserId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 삭제가 완료되었습니다.")
                        .build());
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> detailCard(@RequestParam("cardName") String cardName){

        CardDetailResponseDTO response= cardService.detailCard(cardName);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 카드 상세 조회")
                .data(response)
                .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/search")
    public ResponseEntity<ResponseDTO> searchCard(@RequestParam("company") String company, @RequestParam("type") String type,
            @RequestParam("cardName") String cardName, @RequestParam("userId") String userId
            /*@AuthenticationPrincipal UserAuthentication userAuthentication*/ ){

        /**
         * TO DO :: Get에서 DTO 생성 시 Validation할 방법 찾기
         **/
        CardSearchRequestDTO cardSearchRequestDTO=new CardSearchRequestDTO(company,type,cardName,userId);

        List<CardResponseDTO> dto= cardService.searchCard(cardSearchRequestDTO, cardSearchRequestDTO.getUserId());
        HashMap<String,Object> response=new HashMap<>();
        response.put("cardList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("카드 검색")
                .data(response)
                .build());
    }
}
