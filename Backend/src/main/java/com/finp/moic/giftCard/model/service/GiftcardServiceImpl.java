package com.finp.moic.giftCard.model.service;

import com.finp.moic.util.database.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GiftcardServiceImpl implements GiftcardService{

    @Override
    public void registGift(MultipartFile multipartFile) throws IOException {

    }
}
