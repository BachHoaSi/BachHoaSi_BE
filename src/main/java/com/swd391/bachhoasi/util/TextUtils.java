package com.swd391.bachhoasi.util;

public class TextUtils {
    private TextUtils (){}
    public static String toCamelCase(String text, boolean isCapitalizeFirst) {
        String[] words = text.split("[\\W_]+");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (isCapitalizeFirst && !word.isEmpty()) { 
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            } else if (!word.isEmpty()) {
                word = word.toLowerCase();
            }
            builder.append(word);
        }
        return builder.toString();
    }

    public static String toCamelCase(String text){
        return toCamelCase(text, false);
    }
}
