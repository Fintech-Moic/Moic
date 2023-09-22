package com.finp.moic.giftCard.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GiftcardService {

    public void registGift(MultipartFile multipartFile) throws IOException;
}
