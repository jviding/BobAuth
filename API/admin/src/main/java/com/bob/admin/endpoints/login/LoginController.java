package com.bob.admin.endpoints.login;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
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

        Request req = new Request("POST", "iam", "login", cookie);

        req.addBodyParams("username", login.username);
        req.addBodyParams("password", login.password);

        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());
        response.addHeader("Set-Cookie", res.getSetCookieHeader());

        return "{}";
    }
}