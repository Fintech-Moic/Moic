package com.finp.moic.shop.model.service;

import com.finp.moic.card.model.repository.jpa.CardBenefitRepository;
import com.finp.moic.giftCard.model.repository.jpa.GiftcardRepository;
import com.finp.moic.shop.model.dto.response.*;
import com.finp.moic.shop.model.repository.ShopRepository;
import com.finp.moic.util.database.service.CacheRedisService;
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
    private final CacheRedisService cacheRedisService;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CardBenefitRepository cardBenefitRepository,
                           GiftcardRepository giftcardRepository, ShopLocationRedisService shopLocationRedisService,
                           CacheRedisService cacheRedisService) {
        this.shopRepository = shopRepository;
        this.cardBenefitRepository = cardBenefitRepository;
        this.giftcardRepository = giftcardRepository;
        this.shopLocationRedisService = shopLocationRedisService;
        this.cacheRedisService = cacheRedisService;
    }

    @Override
    public ShopDetailResponseDTO detailShop(String shopName, String shopLocation) {

        /** Validation, RDB Access **/
        ShopDetailResponseDTO dto=shopRepository
                .findByNameAndLocation(shopName,shopLocation)
                .orElseThrow(()->new NotFoundException(ExceptionEnum.SHOP_NOT_FOUND));

        List<BenefitResponseDTO> benefitDTOList=cardBenefitRepository.findAllByShopName(shopName);
        List<GiftResponseDTO> giftcardDTOList=giftcardRepository.findAllByShopName(shopName);

        /** DTO Builder **/
        dto.setBenefits(benefitDTOList);
        dto.setGifts(giftcardDTOList);

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> searchShop(String keyword, double latitude, double longitude, String userId) {

        /** Validation, Redis Access **/
        if(!cacheRedisService.existUserBenefitShopKey(userId)){
            List<String> benefitShopNameList=cardBenefitRepository.findAllShopNameByUserId(userId);
            cacheRedisService.setUserBenefitShopList(benefitShopNameList,userId);
        }

        if(!cacheRedisService.existUserGiftShopKey(userId)){
            List<String> giftShopNameList=giftcardRepository.findAllShopNameByUserId(userId);
            cacheRedisService.setUserGiftShopList(giftShopNameList,userId);
        }

        /** RDB Access **/
        String shopName=shopRepository.findShopNameByKeyword(keyword);

        /** Redis Access **/
        List<ShopSearchResponseDTO> dto=shopLocationRedisService.searchShopListNearByUser(shopName,latitude,longitude);

        /** DTO Builder **/
        for(int idx=0;idx<dto.size();idx++){
            if(cacheRedisService.existUserBenefitShop(dto.get(idx).getShopName(),userId))
                dto.get(idx).setBenefits(true);
            if(cacheRedisService.existUserGiftShop(dto.get(idx).getShopName(),userId))
                dto.get(idx).setGifts(true);
        }

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> getShopListByCategory(String category, double latitude, double longitude, String userId) {

        /** Validation, Redis Access **/
        if(!cacheRedisService.existUserBenefitShopKey(userId)){
            List<String> benefitShopNameList=cardBenefitRepository.findAllShopNameByUserId(userId);
            cacheRedisService.setUserBenefitShopList(benefitShopNameList,userId);
        }

        if(!cacheRedisService.existUserGiftShopKey(userId)){
            List<String> giftShopNameList=giftcardRepository.findAllShopNameByUserId(userId);
            cacheRedisService.setUserGiftShopList(giftShopNameList,userId);
        }

        /** RDB Access **/
        List<String> shopNameList=shopRepository.findAllShopNameByCategory(category);

        /** DTO Builder **/
        List<ShopSearchResponseDTO> dto=new ArrayList<>();
        for(String shopName:shopNameList) {
            /** Redis Access **/
            dto.addAll(shopLocationRedisService.searchShopListNearByUser(shopName,latitude,longitude));
        }

        /** DTO Builder **/
        for(int idx=0;idx<dto.size();idx++){
            if(cacheRedisService.existUserBenefitShop(dto.get(idx).getShopName(),userId))
                dto.get(idx).setBenefits(true);
            if(cacheRedisService.existUserGiftShop(dto.get(idx).getShopName(),userId))
                dto.get(idx).setGifts(true);
        }

        return dto;
    }
}
