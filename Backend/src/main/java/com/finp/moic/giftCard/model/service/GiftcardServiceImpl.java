package com.finp.moic.giftCard.model.service;

import com.finp.moic.util.database.service.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GiftcardServiceImpl implements GiftcardService{

    private final S3ServiceImpl s3Service;

    @Autowired
    public GiftcardServiceImpl(S3ServiceImpl s3Service) {
        this.s3Service = s3Service;
    }

    public String regist (MultipartFile multipartFile){

        // 1. file을 S3에 저장한 후 URL을 만들어놓는다.
        String filePath = s3Service.uploadFile(multipartFile);

        // 2. 만들어 둔 URL로 클로바 api를 호출한다.

        // 3. 클로바 api의 결과값을 챗 gpt에게 보내준다.

        // 4. 챗 gpt의 결과를

        return filePath;
    }
}
