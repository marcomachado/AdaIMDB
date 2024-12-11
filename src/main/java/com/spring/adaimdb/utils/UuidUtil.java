package com.spring.adaimdb.utils;

public class UuidUtil {
    public static String clean(String uuid) {
        return uuid.split(":")[1].replace("\"", "").replace("}", "").trim();
    }
}
