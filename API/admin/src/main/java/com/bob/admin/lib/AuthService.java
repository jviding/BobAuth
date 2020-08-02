package com.bob.admin.lib;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
import com.bob.admin.lib.http.Response;

import org.json.simple.JSONObject;

public class AuthService {
    
    public static boolean isAdmin(String cookie) {
        try {
            
            Request req = new Request("GET", "iam", "profile", cookie);
            
            Response res = RequestService.send(req);

            if (res.getResponseCode() == 200) {
                JSONObject profile = res.getResponseBodyAsJSONObject();
                return Boolean.parseBoolean(profile.get("isAdmin").toString());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

}