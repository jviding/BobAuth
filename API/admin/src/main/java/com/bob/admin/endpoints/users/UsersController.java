package com.bob.admin.endpoints.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
import com.bob.admin.lib.http.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

        Request req = new Request("GET", "iam", "users", cookie);
        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());

        if (res.getResponseCode() == 200) {
            try {
                JSONParser parser = new JSONParser();
                JSONObject resJson = (JSONObject) parser.parse(res.getResponseBody());
                return resJson.toJSONString();   
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "{}";
    }
}