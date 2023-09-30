package com.finp.moic.giftCard.model.service;

import com.finp.moic.giftCard.model.dto.response.GiftcardBrandResponseDTO;
import com.finp.moic.giftCard.model.dto.response.GiftcardListResponseDTO;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.repository.jpa.GiftcardBrandRepository;
import com.finp.moic.giftCard.model.repository.jpa.GiftcardRepository;
import com.finp.moic.user.model.entity.User;
import com.finp.moic.user.model.repository.UserRepository;
import com.finp.moic.util.service.ChatGptService;
import com.finp.moic.util.database.service.CacheRedisService;
import com.finp.moic.util.service.NaverOcrService;
import com.finp.moic.util.database.service.S3Service;
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

    private final S3Service s3Service;
    private final NaverOcrService naverOcrService;
    private final CacheRedisService cacheRedisService;
    private final ChatGptService chatGptService;
    private final UserRepository userRepository;
    private final GiftcardRepository giftcardRepository;
    private final GiftcardBrandRepository giftcardBrandRepository;

    @Autowired
    public GiftcardServiceImpl(S3Service s3Service, NaverOcrService naverOcrService, CacheRedisService cacheRedisService,
                               ChatGptService chatGptService, UserRepository userRepository, GiftcardRepository giftcardRepository,
                               GiftcardBrandRepository giftcardBrandRepository) {
        this.s3Service = s3Service;
        this.naverOcrService = naverOcrService;
        this.cacheRedisService = cacheRedisService;
        this.chatGptService = chatGptService;
        this.userRepository = userRepository;
        this.giftcardRepository = giftcardRepository;
        this.giftcardBrandRepository = giftcardBrandRepository;
    }

    public void regist (String id, MultipartFile multipartFile){

        String filePath = s3Service.uploadFile(multipartFile);
        String originalName = multipartFile.getOriginalFilename();
        List<String> texts =
                naverOcrService.naverOcrApi(filePath, originalName.substring(originalName.lastIndexOf(".") + 1));

        String content = chatGptService.response(texts);

        /**
         * TO DO :: 로그 삭제
         * 성재 : 기프티콘 테스트가 다양하게 이루어 질 때 까지 결과값 확인을 위해 로그를 남겨두겠음.
         **/
        System.out.println(content);

        String[] lines = content.split("\n");
        String shopName= parseShopName(lines[0]);
        LocalDate localDate = parseLocalDate(lines[1]);

        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(ExceptionEnum.USER_NOT_FOUND));


        GiftcardBrandResponseDTO categoryDTO=giftcardBrandRepository.findByName(shopName);

        Giftcard giftcard = Giftcard.builder()
                .user(user)
                .mainCategory(categoryDTO.getMainCategory())
                .category(categoryDTO.getCategory())
                .shopName(shopName)
                .imageUrl(filePath)
                .dueDate(localDate)
                .build();

        giftcardRepository.save(giftcard);

        /*** Redis Access ***/
        /* 혜지 : 값 업데이트가 되었으므로 캐싱 데이터 삭제 */
        cacheRedisService.removeUserGiftShop(id);

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

        Giftcard giftcard = giftcardRepository.findByImageUrl(imageUrl)
                .orElseThrow(() -> new NotFoundException(ExceptionEnum.GIFTCARD_NOT_FOUND));
        s3Service.deleteGiftcard(imageUrl);
        giftcardRepository.delete(giftcard);
    }


    /**
     * TO DO :: 캐시에 List 존재할 시 바로 return 하도록 추가해야 함.
     **/
    public List<GiftcardListResponseDTO> mygifts(String id) {

        return giftcardRepository.findAllByUserId(id);
    }
}
