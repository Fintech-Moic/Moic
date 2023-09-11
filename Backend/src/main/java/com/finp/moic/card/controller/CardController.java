package com.finp.moic.card.controller;

import com.finp.moic.card.model.dto.response.CardGetListResponseDTO;
import com.finp.moic.card.model.dto.response.CardRegistResponseDTO;
import com.finp.moic.card.model.dto.request.CardRegistRequestDTO;
import com.finp.moic.card.model.service.CardServiceImpl;
import com.finp.moic.util.dto.ResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardServiceImpl cardServiceImpl;

    @Autowired
    public CardController(CardServiceImpl cardServiceImpl) {
        this.cardServiceImpl = cardServiceImpl;
    }

    /* 혜지 : 인덱스 테스트 코드. 추후 삭제 예정 */
    @PostMapping("/regist")
    public ResponseEntity<ResponseDTO> registCard(@RequestBody @Valid CardRegistRequestDTO cardRegistRequestDTO){

        CardRegistResponseDTO response= cardServiceImpl.registCard(cardRegistRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                        .message("인덱스 테스트 완료")
                        .data(response)
                        .build());
    }

//    @GetMapping("/all")
//    public ResponseEntity<ResponseDTO> getCardList(@RequestHeader("Authorization") String authorization){
//        String token = authorization.split(" ")[1];
//
//        CardGetListResponseDTO response=cardServiceImpl.getCardList(token);
//    }
}
