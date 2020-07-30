package com.bob.admin.endpoints.user;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
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

        Request req = new Request("PUT", "iam", "user", cookie);

        req.addBodyParams("userID", put.userID);
        req.addBodyParams("email", put.email);
        req.addBodyParams("isAdmin", put.isAdmin);

        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());

        if (res.getResponseCode() == 200) {
            try {
                return res.getResponseBodyAsJSONString();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "{}";
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
        
        Request req = new Request("DELETE", "iam", "user", cookie);

        req.addBodyParams("userID", delete.userID);

        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());

        if (res.getResponseCode() == 200) {
            try {
                return res.getResponseBodyAsJSONString();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "{}";
    }

}