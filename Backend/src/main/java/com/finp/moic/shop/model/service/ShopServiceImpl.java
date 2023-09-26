package com.finp.moic.shop.model.service;

import com.finp.moic.card.model.repository.jpa.CardBenefitRepository;
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

    @Override
    public ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO) {

        /** Validation, RDB Access **/
        ShopDetailResponseDTO dto=shopRepository
                .findByNameAndLocation(shopDetailRequestDTO.getShopName(),shopDetailRequestDTO.getShopLocation())
                .orElseThrow(()->new NotFoundException(ExceptionEnum.SHOP_NOT_FOUND));

        List<BenefitResponseDTO> benefitDTOList=cardBenefitRepository.findAllByShopName(shopDetailRequestDTO.getShopName());
        List<GiftResponseDTO> giftcardDTOList=giftcardRepository.findAllByShopName(shopDetailRequestDTO.getShopName());

        /** DTO Builder **/
        dto.setBenefits(benefitDTOList);
        dto.setGifts(giftcardDTOList);

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> searchShop(ShopSearchRequestDTO shopSearchRequestDTO) {

        /**
         * CONFIRM :: 현재 로직
         * keyword 기반으로 shop에서 검색된 첫번째 결과에 대해
         * -> 내 위치 기반으로 반경 1km 내 가까운 순 정렬
         * -> 각 shop에 대해 cardbenefit과 giftcard 여부 모두 체크해 true/false로 반환
         **/

        /** RDB Access **/
        String shopName=shopRepository.findShopNameByKeyword(shopSearchRequestDTO.getKeyword());

        /** Redis Access **/
        List<ShopSearchResponseDTO> dto=shopLocationRedisService.searchShopListNearByUser(shopName,
                shopSearchRequestDTO.getLatitude(),shopSearchRequestDTO.getLongitude());

        for(int idx=0;idx<dto.size();idx++){
            /**
             * User Benefit Shop 조회
             **/

            /**
             * User Gift Shop 조회
             **/

        }

        return dto;
    }

    @Override
    public List<ShopSearchResponseDTO> getShopListByCategory(ShopCategoryRequestDTO shopCategoryRequestDTO, String userId) {

        /** RDB Access **/
        List<String> shopNameList=shopRepository.findAllShopNameByCategory(shopCategoryRequestDTO.getCategory());

        List<ShopSearchResponseDTO> dto=new ArrayList<>();
        for(String shopName:shopNameList) {

            /** Redis Access **/
            dto.addAll(
                    shopLocationRedisService.searchShopListNearByUser(shopName,
                    shopCategoryRequestDTO.getLatitude(), shopCategoryRequestDTO.getLongitude())
            );
        }

        for(int idx=0;idx<dto.size();idx++){
            /**
             * User Benefit Shop 조회
             **/

            /**
             * User Gift Shop 조회
             **/

        }

        return dto;
    }
}
