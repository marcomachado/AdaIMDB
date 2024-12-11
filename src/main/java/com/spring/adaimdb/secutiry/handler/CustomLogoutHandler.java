package com.spring.adaimdb.secutiry.handler;


import com.spring.adaimdb.monitor.MonitorProgressUsers;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        MonitorProgressUsers.clearUser(username);

        try {
            response.sendRedirect("/login");
        } catch (IOException e) {
            response.sendRedirect("/");
        }
    }
}

