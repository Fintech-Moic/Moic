package com.finp.moic.util.database.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

    public String uploadFile(MultipartFile multipartFile);

    public void deleteFile(String filePath);
}
