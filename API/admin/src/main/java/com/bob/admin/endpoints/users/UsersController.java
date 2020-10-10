package com.bob.admin.endpoints.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;


@RestController
public class UsersController {

    @GetMapping(
        value = "/users", 
        produces = "application/json"
    )
    public String getUsers(
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        Request req = new Request("GET", "iam", "users");
        Response res = req.send(cookie);
        response.setStatus(res.getResponseCode());

        System.out.println(res.getResponseCode());

        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            return "{}";
        }
       
    }
}