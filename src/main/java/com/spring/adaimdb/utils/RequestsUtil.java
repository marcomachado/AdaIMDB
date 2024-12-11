package com.spring.adaimdb.utils;

import jakarta.servlet.http.HttpServletRequest;

public class RequestsUtil {

     public static String getNameUserPrincipal(HttpServletRequest request) {
        return request.getUserPrincipal().getName();
    }
}
