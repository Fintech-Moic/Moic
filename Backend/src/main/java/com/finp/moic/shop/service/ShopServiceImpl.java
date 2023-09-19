package com.finp.moic.shop.service;

import com.finp.moic.shop.model.dto.request.LocationRequestDTO;
import com.finp.moic.shop.model.dto.response.LocationResponseDTO;
import com.finp.moic.shop.model.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService{

    private ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public LocationResponseDTO testJavaLocation(LocationRequestDTO locationRequestDTO) {
        return null;
    }

    @Override
    public LocationResponseDTO testRDBLocation(LocationRequestDTO locationRequestDTO) {
        return null;
    }

    @Override
    public LocationResponseDTO testRedisLocation(LocationRequestDTO locationRequestDTO) {
        return null;
    }
}
