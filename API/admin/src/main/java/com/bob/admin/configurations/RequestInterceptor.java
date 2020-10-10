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
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if (c.getName().equals("sessionid")) {
                    return AuthService.isAdmin(c.getValue());
                }
            }
        }
        return false;
    }

    private void printHttpLog(HttpServletRequest request, HttpServletResponse response) {
        String timeStamp = getTimeStamp();
        String method = request.getMethod();
        String path = request.getRequestURI();    
        String httpProtocol = request.getProtocol();
        int status = response.getStatus();
        System.out.println("[" + timeStamp + "] \"" + method + " " + path + " " + httpProtocol + "\" " + status);
    }

    private String getTimeStamp() {
        //java.text.SimpleDateFormat. "dd/LLL/yyyy"
        //(.format (java.text.SimpleDateFormat. "dd/LLL/yyyy") (new java.util.Date))
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/LLL/yyyy HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean IS_LOGIN_REQUEST = request.getRequestURI().equals("/login");
        boolean IS_ADMIN = isAdmin(request);
        if (IS_LOGIN_REQUEST) {
            return true;
        } else if (IS_ADMIN) {
            return true;
        } else {
            response.setStatus(403);
            printHttpLog(request, response);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        printHttpLog(request, response);
    }

}
