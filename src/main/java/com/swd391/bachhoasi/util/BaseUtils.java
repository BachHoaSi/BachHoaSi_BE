package com.swd391.bachhoasi.util;

import java.util.UUID;

public class BaseUtils {
    private BaseUtils(){}
    public static String generateFileName(String originFileName) {
        return String.format("%s-%s",originFileName,UUID.randomUUID().toString());
    }
}
