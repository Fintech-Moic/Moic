package com.finp.moic.giftCard.controller;

import com.finp.moic.giftCard.model.service.GiftcardServiceImpl;
import com.finp.moic.util.database.service.S3ServiceImpl;
import com.finp.moic.util.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/gift")
public class GiftcardController {

    private final GiftcardServiceImpl giftcardService;
    private final S3ServiceImpl s3Service;

    @Autowired
    public GiftcardController(GiftcardServiceImpl giftcardService, S3ServiceImpl s3Service) {
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
    public ResponseEntity<ResponseDTO> delete(String imageUrl) {

        giftcardService.delete(imageUrl);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO.builder()
                .message("삭제가 완료되었습니다.")
                .build());
    }


}
