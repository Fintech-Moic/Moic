package com.finp.moic.card.controller;

import com.finp.moic.card.model.dto.request.*;
import com.finp.moic.card.model.dto.response.*;
import com.finp.moic.card.model.service.CardService;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/card")
@Validated
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

        String userId="test1111";

        cardService.registCard(cardRegistRequestDTO, /*userAuthentication.getId()*/userId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 등록이 완료되었습니다.")
                        .build());
    }

    /**
     * TO DO :: userId 삭제 및 주석 해제
     * **/
    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/){

        String userId="test1111";

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
    public ResponseEntity<ResponseDTO> getMyCardList(/*@AuthenticationPrincipal UserAuthentication userAuthentication*/) {

        String userId="test1111";

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

        String userId="test1111";

        cardService.deleteCard(cardDeleteRequestDTO,/*userAuthentication.getId()*/userId);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("카드 삭제가 완료되었습니다.")
                        .build());
    }

    @GetMapping("/detail")
    public ResponseEntity<ResponseDTO> detailCard(@RequestParam("cardName") @NotBlank String cardName){

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
    public ResponseEntity<ResponseDTO> searchCard(@RequestParam("company") @NotNull String company, @RequestParam("type") @NotNull String type,
                                                  @RequestParam("cardName") @NotNull String cardName
            /*@AuthenticationPrincipal UserAuthentication userAuthentication*/ ){

        String userId="test1111";

        List<CardResponseDTO> dto= cardService.searchCard(company,type,cardName,/*userAuthentication.getId()*/userId);
        HashMap<String,Object> response=new HashMap<>();
        response.put("cardList",dto);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("카드 검색")
                .data(response)
                .build());
    }
}
