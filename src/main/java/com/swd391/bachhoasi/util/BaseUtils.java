package com.swd391.bachhoasi.util;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.swd391.bachhoasi.model.exception.ValidationFailedException;


public class BaseUtils {
    private BaseUtils(){}
    public static String generateFileName(MultipartFile multipartFile) {
        String originFileName = multipartFile.getOriginalFilename();
        if(originFileName == null || originFileName.isEmpty()) {
            throw new ValidationFailedException("Name should not be empty");
        }
        if(multipartFile.isEmpty()) {
            throw new ValidationFailedException("Should not use empty files");
        }
        String finalOriginFileName = originFileName.substring(0,originFileName.lastIndexOf("."));
        return String.format("%s-%s",finalOriginFileName,UUID.randomUUID().toString());
    }
}
