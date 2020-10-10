package com.bob.admin.configurations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor 
  extends HandlerInterceptorAdapter {
 
    private boolean isAdmin(HttpServletRequest request) {
        for (Cookie c : request.getCookies()) {
            if (c.getName().equals("sessionid")) {
                return AuthService.isAdmin(c.getValue());
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean NOT_LOGIN_REQUEST = !request.getRequestURI().equals("/login");
        boolean IS_ADMIN = isAdmin(request);
        if (NOT_LOGIN_REQUEST && IS_ADMIN) {
            return true;
        } else {
            response.setStatus(403);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String timeStamp = getTimeStamp();
        String method = request.getMethod();
        String path = request.getRequestURI();    
        String httpProtocol = request.getProtocol();
        int status = response.getStatus();

        System.out.println("[" + timeStamp + "] \"" + method + " " + path + " " + httpProtocol + "\" " + status);
    }

    private String getTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/LLL/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
