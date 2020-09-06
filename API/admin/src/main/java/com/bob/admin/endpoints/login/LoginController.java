package com.bob.admin.endpoints.login;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.JsonRequest;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {
    
    @PostMapping(
        value = "/login", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String login(
        @RequestBody LoginJson login, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        JsonRequest req = new JsonRequest("POST", "iam", "login");
        req.addBodyParams("username", login.username);
        req.addBodyParams("password", login.password);
        Response res = req.send(cookie);
        response.setStatus(res.getResponseCode());
        if (res.hasSessionCookie()) {
            response.addHeader("Set-Cookie", res.getSessionCookie());
        }
        return "{}";
    }
}