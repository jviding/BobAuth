package com.bob.admin.endpoints.logout;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class LogoutController {
    
    @PostMapping(
        value = "/logout", 
        produces = "application/json"
    )
    public String logout(
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        Request req = new Request("POST", "iam", "logout");
        Response res = req.send(cookie);
        if (res.getResponseCode() == 200 && res.hasSessionCookie()) {
            response.addHeader("Set-Cookie", res.getSessionCookie());
        } else {
            response.setStatus(400);
        }
        return "{}";
    }
}