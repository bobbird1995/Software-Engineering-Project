package com.example.csgs.utils;

import java.util.regex.Pattern;

public class IsIntegerUtil {
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
