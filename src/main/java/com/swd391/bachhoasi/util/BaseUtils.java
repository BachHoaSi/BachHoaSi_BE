package com.swd391.bachhoasi.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.swd391.bachhoasi.model.exception.ValidationFailedException;


public class BaseUtils {
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";
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

    public static String generateProductCode() {
        // Prefix
        String prefix = "BHS";
        // Generate a sequence of 10 random digits
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom randomized = new SecureRandom();
        for (int i = 0; i < 10; i++) {
            int randomDigit = randomized.nextInt(10); // Generates a random digit between 0 and 9
            stringBuilder.append(randomDigit);
        }
        return prefix + stringBuilder;
    }

    public static String generatePassword(int length) {
        var passwordGen = new PasswordGenerator.PasswordGeneratorBuilder()
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .build();
        return passwordGen.generate(length);
    }

    public static BigDecimal genRandomBigDecimalId() {
        Random random = new Random();
        BigInteger integerPart = new BigInteger(128, random); // Generate a 128-bit random integer
        int scale = random.nextInt(10) + 1; // Random scale between 1 and 10 
        return new BigDecimal(integerPart).setScale(scale, RoundingMode.HALF_UP);
    }
}
