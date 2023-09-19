package com.finp.moic.shop.service;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;
import com.finp.moic.shop.model.dto.response.ShopResponseDTO;
import com.finp.moic.shop.model.entity.Shop;
import com.finp.moic.shop.model.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{

    private ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public LocationResponseDTO testJavaLocation(LocationRequestDTO locationRequestDTO) {

        long start=System.nanoTime();

        List<Shop> shopList=shopRepository.findAll();

        double myLat=locationRequestDTO.getLatitude();
        double myLng=locationRequestDTO.getLongitude();

        List<ShopResponseDTO> shopDTO=new ArrayList<>();
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
                    ShopResponseDTO.builder()
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

//

        long end=System.nanoTime();

        double time=(end-start)/1000000.0;
        return null;
    }

    @Override
    public LocationResponseDTO testRedisLocation(LocationRequestDTO locationRequestDTO) {
        long start=System.nanoTime();

//

        long end=System.nanoTime();

        double time=(end-start)/1000000.0;
        return null;
    }
}
