package com.finp.moic.giftCard.model.service;

import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.repository.GiftcardRepository;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.database.service.ChatGptService;
import com.finp.moic.util.database.service.CacheRedisService;
import com.finp.moic.util.database.service.NaverOcrService;
import com.finp.moic.util.database.service.S3ServiceImpl;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GiftcardServiceImpl{

    private final S3ServiceImpl s3Service;
    private final NaverOcrService naverOcrService;
    private final CacheRedisService cacheRedisService;
    private final ChatGptService chatGptService;
    private final UserRepository userRepository;
    private final GiftcardRepository giftcardRepository;

    @Autowired
    public GiftcardServiceImpl(S3ServiceImpl s3Service, NaverOcrService naverOcrService, CacheRedisService cacheRedisService, ChatGptService chatGptService, UserRepository userRepository, GiftcardRepository giftcardRepository) {
        this.s3Service = s3Service;
        this.naverOcrService = naverOcrService;
        this.cacheRedisService = cacheRedisService;
        this.chatGptService = chatGptService;
        this.userRepository = userRepository;
        this.giftcardRepository = giftcardRepository;
    }

    public void regist (String id, MultipartFile multipartFile){

        String filePath = s3Service.uploadFile(multipartFile);
        String originalName = multipartFile.getOriginalFilename();
        List<String> texts =
                naverOcrService.naverOcrApi(filePath, originalName.substring(originalName.lastIndexOf(".") + 1));

        String content = chatGptService.response(texts);

        System.out.println(content);

        String[] lines = content.split("\n");
        String shopName= parseShopName(lines[0]);
        LocalDate localDate = parseLocalDate(lines[1]);

        User user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(ExceptionEnum.USER_NOT_FOUND));


        /**
         * 성재 : category 데이터 정제 완료시 수정해야 함.
         */
        Giftcard giftcard = Giftcard.builder()
                .user(user)
                .category("임의")
                .shopName(shopName)
                .imageUrl(filePath)
                .dueDate(localDate)
                .build();

        giftcardRepository.save(giftcard);

    }

    public String parseShopName(String line) {
        return line.replace("상호명:","").trim();
    }
    public LocalDate parseLocalDate(String line) {
        String dataStr = line.replace("유효기간:","").trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dataStr,formatter);
        return localDate;
    }

    public void delete(String imageUrl) {

        Giftcard giftcard = giftcardRepository.findByImageUrl(imageUrl).orElseThrow(() -> new NotFoundException(ExceptionEnum.GIFTCARD_NOT_FOUND));
        s3Service.deleteGiftcard(imageUrl);
        giftcardRepository.delete(giftcard);
    }
}
