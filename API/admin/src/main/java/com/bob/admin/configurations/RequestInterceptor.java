package com.bob.admin.configurations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RequestInterceptor 
  extends HandlerInterceptorAdapter {
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return true;
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
