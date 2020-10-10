package com.bob.admin.endpoints.user;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.JsonRequest;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserController {

    @PutMapping(
        value = "/user",
        consumes = "application/json", 
        produces = "application/json"
    )
    public String updateUser(
        @RequestBody PutUser put, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        JsonRequest req = new JsonRequest("PUT", "iam", "user");
        req.addBodyParams("userID", put.userID);
        req.addBodyParams("email", put.email);
        req.addBodyParams("isAdmin", put.isAdmin);
        Response res = req.send(cookie);
        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            response.setStatus(400);
            return "{}";
        }
    }

    @DeleteMapping(
        value = "/user",
        consumes = "application/json", 
        produces = "application/json"
    )
    public String deleteUser(
        @RequestBody DeleteUser delete, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        JsonRequest req = new JsonRequest("DELETE", "iam", "user");
        req.addBodyParams("userID", delete.userID);
        Response res = req.send(cookie);
        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            response.setStatus(400);
            return "{}";
        }
    }

}