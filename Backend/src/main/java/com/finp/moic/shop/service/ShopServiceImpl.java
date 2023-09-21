package com.finp.moic.shop.service;

import com.finp.moic.card.model.entity.CardBenefit;
import com.finp.moic.card.model.repository.jpa.CardBenefitRepository;
import com.finp.moic.giftCard.model.entity.Giftcard;
import com.finp.moic.giftCard.model.repository.GiftcardRepository;
import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopDetailRequestDTO;
import com.finp.moic.shop.model.dto.request.ShopSearchRequestDTO;
import com.finp.moic.shop.model.dto.response.*;
import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.shop.model.repository.ShopRepository;
import com.finp.moic.util.exception.ExceptionEnum;
import com.finp.moic.util.exception.list.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{

    private final ShopRepository shopRepository;
    private final CardBenefitRepository cardBenefitRepository;
    private final GiftcardRepository giftcardRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository, CardBenefitRepository cardBenefitRepository,
                           GiftcardRepository giftcardRepository) {
        this.shopRepository = shopRepository;
        this.cardBenefitRepository = cardBenefitRepository;
        this.giftcardRepository = giftcardRepository;
    }

    @Override
    public LocationResponseDTO testJavaLocation(LocationRequestDTO locationRequestDTO) {

        long start=System.nanoTime();

        List<Shop> shopList=shopRepository.findAll();

        double myLat=locationRequestDTO.getLatitude();
        double myLng=locationRequestDTO.getLongitude();

        List<TestShopResponseDTO> shopDTO=new ArrayList<>();
        for(Shop shop:shopList){

            double shopLat=shop.getLatitude();
            double shopLng=shop.getLongitude();

            double theta = myLng - shopLng;
            double dist = Math.sin((myLat * Math.PI/180.0))* Math.sin((shopLat * Math.PI/180.0))
                    + Math.cos((myLat * Math.PI/180.0))*Math.cos((shopLat * Math.PI/180.0))*Math.cos((theta * Math.PI/180.0));
            dist = Math.acos(dist);
            dist = (dist * 180 / Math.PI);
            dist = dist * 60*1.1515*1609.344;

            shopDTO.add(
                    TestShopResponseDTO.builder()
                            .shopName(shop.getName())
                            .address(shop.getAddress())
                            .latitude(shop.getLatitude())
                            .longitude(shop.getLongitude())
                            .distance(dist)
                            .build()
            );
        }
        Collections.sort(shopDTO);

        long end=System.nanoTime();

        double time=(end-start)/1000000.0;

        LocationResponseDTO dto=LocationResponseDTO.builder()
                .shop(shopDTO)
                .time(time)
                .build();

        return dto;
    }

    @Override
    public LocationResponseDTO testRDBLocation(LocationRequestDTO locationRequestDTO) {

        long start=System.nanoTime();

        List<Shop> shopList=shopRepository.findAll(); //

        double myLat=locationRequestDTO.getLatitude();
        double myLng=locationRequestDTO.getLongitude();

        List<TestShopResponseDTO> shopDTO=new ArrayList<>();
        for(Shop shop:shopList){

            shopDTO.add(
                    TestShopResponseDTO.builder()
                            .shopName(shop.getName())
                            .address(shop.getAddress())
                            .latitude(shop.getLatitude())
                            .longitude(shop.getLongitude())
                            //.distance(null)
                            .build()
            );
        }
        Collections.sort(shopDTO);

        long end=System.nanoTime();

        double time=(end-start)/1000000.0;

        LocationResponseDTO dto=LocationResponseDTO.builder()
                .shop(shopDTO)
                .time(time)
                .build();

        return dto;
    }

    @Override
    public LocationResponseDTO testRedisLocation(LocationRequestDTO locationRequestDTO) {
        long start=System.nanoTime();

//

        long end=System.nanoTime();

        double time=(end-start)/1000000.0;
        return null;
    }

    @Override
    public ShopDetailResponseDTO detailShop(ShopDetailRequestDTO shopDetailRequestDTO) {

        /** Validation, RDB Access **/
        Shop shop=shopRepository.findByNameAndLocation(shopDetailRequestDTO.getShopName(),shopDetailRequestDTO.getShopLocation());
        if(shop==null) throw new NotFoundException(ExceptionEnum.SHOP_NOT_FOUND);

        /** RDB Access **/
        /* 혜지 : QueryDSL */
        List<CardBenefit> cardBenefitList=cardBenefitRepository.findAllByShopName(shopDetailRequestDTO.getShopName());
        /* 혜지 : JPQL */
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
                .benefits(benefitDTOList)
                .gifts(giftDTOList)
                .build();

        return dto;
    }

    @Override
    public ShopSearchResponseDTO searchShop(ShopSearchRequestDTO shopSearchRequestDTO) {



        return null;
    }
}
