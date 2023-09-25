package com.finp.moic.shop.model.service;

import com.finp.moic.card.model.entity.CardBenefit;
import com.finp.moic.card.model.repository.jpa.CardBenefitRepository;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.repository.GiftcardRepository;
import com.finp.moic.shop.model.dto.request.ShopCategoryRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.*;
import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.shop.model.repository.ShopRepository;
import com.finp.moic.util.database.entity.ShopLocationRedisDTO;
import com.finp.moic.util.database.service.ShopLocationRedisService;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{

    private final ShopRepository shopRepository;
    private final CardBenefitRepository cardBenefitRepository;
    private final GiftcardRepository giftcardRepository;
    private final ShopLocationRedisService shopLocationRedisService;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CardBenefitRepository cardBenefitRepository,
                           GiftcardRepository giftcardRepository, ShopLocationRedisService shopLocationRedisService) {
        this.shopRepository = shopRepository;
        this.cardBenefitRepository = cardBenefitRepository;
        this.giftcardRepository = giftcardRepository;
        this.shopLocationRedisService = shopLocationRedisService;
    }

    /**
     * CONFIRM :: TEST용. 추후 삭제
     **/
    @Override
    public ShopLocationRedisDTO testRedisLocation(){

        List<ShopLocationRedisDTO> dto=shopLocationRedisService.getShopLocationListNearByUser("스타벅스",37.5013068,127.0396597,5);

        return dto.get(0);
    }

    @Override
    public ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO) {

        /** Validation, RDB Access **/
        Shop shop=shopRepository.findShopDetail(shopDetailRequestDTO.getShopName(),shopDetailRequestDTO.getShopLocation());
        if(shop==null) throw new NotFoundException(ExceptionEnum.SHOP_NOT_FOUND);

        /** RDB Access **/
        List<CardBenefit> cardBenefitList=cardBenefitRepository.findAllByShopName(shopDetailRequestDTO.getShopName());
        List<Giftcard> giftcardList=giftcardRepository.findAllByShopName(shopDetailRequestDTO.getShopName());

        /** DTO Builder **/
        List<BenefitResponseDTO> benefitDTOList=new ArrayList<>();
        for(CardBenefit cardBenefit:cardBenefitList){
            benefitDTOList.add(
                    BenefitResponseDTO.builder()
                            .cardName(cardBenefit.getCard().getName())
                            .content(cardBenefit.getContent())
                            .discount(cardBenefit.getDiscount())
                            .point(cardBenefit.getPoint())
                            .cashBack(cardBenefit.getCashback())
                            .build()
            );
        }

        List<GiftResponseDTO> giftDTOList=new ArrayList<>();
        for(Giftcard giftcard:giftcardList){
            giftDTOList.add(
              GiftResponseDTO.builder()
                      .productName(giftcard.getProductName())
                      .barcodeImage(giftcard.getBarcodeImage())
                      .barcodeNumber(giftcard.getBarcodeNumber())
                      .dueDate(String.valueOf(giftcard.getDueDate())) //날짜 String으로 변경
                      .build()
            );
        }

        ShopDetailResponseDTO dto=ShopDetailResponseDTO.builder()
                .category(shop.getCategory())
                .shopName(shop.getName())
                .shopLocation(shop.getLocation())
                .address(shop.getAddress())
                .latitude(shop.getLatitude())
                .longitude(shop.getLongitude())
                .benefits(benefitDTOList)
                .gifts(giftDTOList)
                .build();

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> searchShop(ShopSearchRequestDTO shopSearchRequestDTO) {

        /**
         * CONFIRM :: 로직
         * 현재 로직 : keyword 기반으로 shop에서 검색된 첫번째 결과에 대해
         * -> 내 위치 기반으로 반경 3km 내 가까운 순 정렬
         * -> 각 shop에 대해 cardbenefit과 giftcard 여부 모두 체크해 true/false로 반환. 그러나 현재 임의로 리턴한다.
         **/

        /**
         * TO DO :: 내가 가진 카드 혜택과 기프티콘 캐시 탐색 후 true/false로 리턴.
         **/

        /** RDB Access **/
        Shop shop=shopRepository.findByKeyword(shopSearchRequestDTO.getKeyword());

        List<ShopSearchResponseDTO> dto=new ArrayList<>();

        List<ShopLocationRedisDTO> redisDTO=shopLocationRedisService.getShopLocationListNearByUser(shop.getName(),
                shopSearchRequestDTO.getLatitude(),shopSearchRequestDTO.getLongitude(),3);
        int size=redisDTO.size();
        for(int i=0;i<size/3;i++){
            dto.add(
                    ShopSearchResponseDTO.builder()
                            .category(redisDTO.get(i).getCategory())
                            .shopName(shop.getName())
                            .shopLocation(redisDTO.get(i).getLocation())
                            .address(redisDTO.get(i).getAddress())
                            .benefits(true)
                            .gifts(true)
                            .build()
            );
        }
        for(int i=size/3+1;i<size/2;i++){
            dto.add(
                    ShopSearchResponseDTO.builder()
                            .category(redisDTO.get(i).getCategory())
                            .shopName(shop.getName())
                            .shopLocation(redisDTO.get(i).getLocation())
                            .address(redisDTO.get(i).getAddress())
                            .benefits(false)
                            .gifts(true)
                            .build()
            );
        }
        for(int i=size/2+1;i<size;i++){
            dto.add(
                    ShopSearchResponseDTO.builder()
                            .category(redisDTO.get(i).getCategory())
                            .shopName(shop.getName())
                            .shopLocation(redisDTO.get(i).getLocation())
                            .address(redisDTO.get(i).getAddress())
                            .benefits(true)
                            .gifts(false)
                            .build()
            );
        }

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> getShopListByCategory(ShopCategoryRequestDTO shopCategoryRequestDTO, String userId) {

        List<Shop> shopList=shopRepository.getShopListByCategory(shopCategoryRequestDTO.getCategory());

        /**
         * TO DO :: 내가 가진 카드 혜택과 기프티콘 캐시 탐색 후 true/false로 리턴.
         **/


        List<ShopSearchResponseDTO> dto=new ArrayList<>();

        for(Shop shop: shopList) {
            List<ShopLocationRedisDTO> redisDTO = shopLocationRedisService.getShopLocationListNearByUser(shop.getName(),
                    shopCategoryRequestDTO.getLatitude(), shopCategoryRequestDTO.getLongitude(), 3);
            int size = redisDTO.size();
            for (int i = 0; i < size / 3; i++) {
                dto.add(
                        ShopSearchResponseDTO.builder()
                                .category(redisDTO.get(i).getCategory())
                                .shopName(shop.getName())
                                .shopLocation(redisDTO.get(i).getLocation())
                                .address(redisDTO.get(i).getAddress())
                                .benefits(true)
                                .gifts(true)
                                .build()
                );
            }
            for (int i = size / 3 + 1; i < size / 2; i++) {
                dto.add(
                        ShopSearchResponseDTO.builder()
                                .category(redisDTO.get(i).getCategory())
                                .shopName(shop.getName())
                                .shopLocation(redisDTO.get(i).getLocation())
                                .address(redisDTO.get(i).getAddress())
                                .benefits(false)
                                .gifts(true)
                                .build()
                );
            }
            for (int i = size / 2 + 1; i < size; i++) {
                dto.add(
                        ShopSearchResponseDTO.builder()
                                .category(redisDTO.get(i).getCategory())
                                .shopName(shop.getName())
                                .shopLocation(redisDTO.get(i).getLocation())
                                .address(redisDTO.get(i).getAddress())
                                .benefits(true)
                                .gifts(false)
                                .build()
                );
            }
        }

        return dto;
    }
}
