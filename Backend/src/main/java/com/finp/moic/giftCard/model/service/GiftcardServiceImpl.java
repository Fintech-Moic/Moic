package com.finp.moic.giftCard.model.service;

import com.finp.moic.util.database.service.ChatGptService;
import com.finp.moic.util.database.service.CacheRedisService;
import com.finp.moic.util.database.service.NaverOcrService;
import com.finp.moic.util.database.service.S3ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
public class GiftcardServiceImpl implements GiftcardService{

    private final S3ServiceImpl s3Service;
    private final NaverOcrService naverOcrService;
    private final CacheRedisService cacheRedisService;
    private final ChatGptService chatGptService;

    @Autowired
    public GiftcardServiceImpl(S3ServiceImpl s3Service, NaverOcrService naverOcrService, CacheRedisService cacheRedisService, ChatGptService chatGptService) {
        this.s3Service = s3Service;
        this.naverOcrService = naverOcrService;
        this.cacheRedisService = cacheRedisService;
        this.chatGptService = chatGptService;
    }

    public String regist (MultipartFile multipartFile){

        // 1. file을 S3에 저장한 후 URL을 만들어놓는다.
        String filePath = s3Service.uploadFile(multipartFile);

        String originalName = multipartFile.getOriginalFilename();
        // 2. 만들어 둔 URL로 클로바 api를 호출한다.
        List<String> texts =
                naverOcrService.naverOcrApi(filePath, originalName.substring(originalName.lastIndexOf(".") + 1));

        // 3. 클로바 api의 결과값을 챗 gpt에게 보내준다.
        Map<String,String> answer = chatGptService.response(texts);
        // 4. 챗 gpt의 결과를


        /*** Redis Access ***/
        /* 혜지 : 값 업데이트가 되었으므로 캐싱 데이터 삭제 */
        //cacheRedisService.removeUserGiftShop(userId);

        return filePath;
    }
}
