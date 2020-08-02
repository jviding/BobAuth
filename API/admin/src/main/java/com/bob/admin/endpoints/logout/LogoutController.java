package com.bob.admin.endpoints.logout;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
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
        try {

            Request req = new Request("POST", "iam", "logout", cookie);
            Response res = RequestService.send(req);

            response.setStatus(res.getResponseCode());
        
            response.addHeader("Set-Cookie", res.getSetCookieHeader());

        } catch (Exception e) {
            System.out.println(e);
        }

        return "{}";
    }
}