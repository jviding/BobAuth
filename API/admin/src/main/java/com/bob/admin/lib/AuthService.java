package com.bob.admin.lib;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.json.simple.JSONObject;

public class AuthService {
    
    public static boolean isAdmin(String cookie) {

        Request req = new Request("GET", "iam", "profile");
        Response res = req.send(cookie);

        if (res.getResponseCode() == 200) {
            try {
                JSONObject profile = res.getResponseBodyAsJSONObject();
                return Boolean.parseBoolean(profile.get("isAdmin").toString());
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        } else {
            return false;
        }
    }

}