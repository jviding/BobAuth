package com.bob.admin.endpoints.profile;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.JsonRequest;
import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ProfileController {
    
    @GetMapping(
        value = "/profile", 
        produces = "application/json"
    )
    public String getProfile(
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        Request req = new Request("GET", "iam", "profile");
        Response res = req.send(cookie);
        response.setStatus(res.getResponseCode());
        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            return "{}";
        }
    }

    @PutMapping(
        value = "/profile", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String updateProfile(
        @RequestBody PutProfile put, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        JsonRequest req = new JsonRequest("PUT", "iam", "profile");
        req.addBodyParams("newEmail", put.newEmail);
        req.addBodyParams("newPassword", put.newPassword);
        req.addBodyParams("password", put.password);
        Response res = req.send(cookie);
        response.setStatus(res.getResponseCode());
        if (res.hasSessionCookie()) {
            response.addHeader("Set-Cookie", res.getSessionCookie());
        }
        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            return "{}";
        }
    }

}