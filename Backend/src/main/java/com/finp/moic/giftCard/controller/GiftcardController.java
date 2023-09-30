package com.finp.moic.giftCard.controller;

import com.finp.moic.giftCard.model.dto.request.GiftcardDeleteRequestDTO;
import com.finp.moic.giftCard.model.dto.response.GiftcardListResponseDTO;
import com.finp.moic.giftCard.model.service.GiftcardServiceImpl;
import com.finp.moic.util.database.service.S3Service;
import com.finp.moic.util.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gift")
public class GiftcardController {

    private final GiftcardServiceImpl giftcardService;
    private final S3Service s3Service;

    @Autowired
    public GiftcardController(GiftcardServiceImpl giftcardService, S3Service s3Service) {
        this.giftcardService = giftcardService;
        this.s3Service = s3Service;
    }

    /**
     * 성재 : Front 코드 작성 완료 시 param에 id 추가해야 함.
     * @param multipartFile
     */
    @PostMapping("/regist")
    @Transactional
    public ResponseEntity<ResponseDTO> regist(@RequestParam(value = "file", required = false)
                                                  MultipartFile multipartFile) {

        giftcardService.regist("test1111",multipartFile);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("등록이 완료되었습니다.")
                .build());
    }

    @PostMapping("/delete")
    public ResponseEntity<ResponseDTO> delete(@RequestBody GiftcardDeleteRequestDTO giftcardDeleteRequestDTO) {

        giftcardService.delete(giftcardDeleteRequestDTO.getImageUrl());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("삭제가 완료되었습니다.")
                .build());
    }

    /**
     * 성재 : Front 코드 작성 완료 시 param에 id 변경해야 함.
     */
    @GetMapping("/mygifts")
    public ResponseEntity<ResponseDTO> mygifts(/*String id*/) {

        String id="test1111";
        List<GiftcardListResponseDTO> list = giftcardService.mygifts(id);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("내 기프티콘 목록 조회")
                .data(list)
                .build());
    }



}
