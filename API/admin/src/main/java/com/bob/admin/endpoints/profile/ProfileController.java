package com.bob.admin.endpoints.profile;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
import com.bob.admin.lib.http.Response;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

        Request req = new Request("GET", "iam", "profile", cookie);
        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());

        if (res.getResponseCode() == 200) {
            try {
                return this.getProfileAsJsonStringWithoutSensitiveFields(res.getResponseBody());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "{}";
    }

    @PutMapping(
        value = "/profile", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String updateProfile(
        @RequestBody UpdateJson update, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {

        Request req = new Request("PUT", "iam", "profile", cookie);

        req.addBodyParams("newEmail", update.newEmail);
        req.addBodyParams("newPassword", update.newPassword);
        req.addBodyParams("password", update.password);

        Response res = RequestService.send(req);

        response.setStatus(res.getResponseCode());
        response.addHeader("Set-Cookie", res.getSetCookieHeader());

        if (res.getResponseCode() == 200) {
            try {
                return this.getProfileAsJsonStringWithoutSensitiveFields(res.getResponseBody());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "{}";
    }

    private String getProfileAsJsonStringWithoutSensitiveFields(String profile) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject resJson = (JSONObject) parser.parse(profile);
        resJson.remove("userID");
        resJson.remove("isAdmin");
        return resJson.toJSONString();
    }
}