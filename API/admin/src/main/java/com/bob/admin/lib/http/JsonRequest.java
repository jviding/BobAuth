package com.bob.admin.lib.http;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JsonRequest extends Request {
    
    private Map<String,String> requestHeaders = new HashMap<>();
    private JSONObject requestBody = new JSONObject();
    

    public JsonRequest(String method, String host, String endpoint, String sessionCookie) {
        this(method, host, endpoint);
        this.requestHeaders.put("Cookie", "sessionid=" + sessionCookie);
    }

    public JsonRequest(String method, String host, String endpoint) {
        super(method, host, endpoint);
        this.requestHeaders.put("Content-Type", "application/json; charset=utf-8");
    }

    @SuppressWarnings("unchecked")
    public void addBodyParams(String key, String value) {
        this.requestBody.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void addBodyParams(String key, int value) {
        this.requestBody.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void addBodyParams(String key, boolean value) {
        this.requestBody.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public void addBodyParams(String key, JSONArray arr) {
        this.requestBody.put(key, arr);
    }

    public Response send() {
        try {
            byte[] body = this.requestBody.toJSONString().getBytes("utf-8");
            return super.send(this.requestHeaders, body);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, "Something went wrong!");
        } 
    }

}