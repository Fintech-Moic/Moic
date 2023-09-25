package com.finp.moic.giftCard.model.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GiftcardService {

    public String regist(MultipartFile multipartFile) throws IOException;
}
