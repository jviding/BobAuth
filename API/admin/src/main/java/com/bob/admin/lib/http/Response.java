package com.bob.admin.lib.http;

import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Response {
    
    private int responseCode;
    private Map<String, List<String>> responseHeaders;
    private String responseBody;

    public Response(int responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public Response(int responseCode, String responseBody, Map<String, List<String>> responseHeaders) {
        this(responseCode, responseBody);
        this.responseHeaders = responseHeaders;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.responseHeaders;
    }

    public Boolean hasSessionCookie() {
        return !getSessionCookie().isEmpty();
    }

    public String getSessionCookie() {
        final List<String> cookies;
        if (this.responseHeaders != null && (cookies = this.responseHeaders.get("Set-Cookie")) != null) {
            for (String cookie : cookies) {
                if (cookie.indexOf("sessionid=") == 0) {
                    return cookie;
                }
            }
        }
        return "";
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public JSONObject getResponseBodyAsJSONObject() throws ParseException {
        return (JSONObject) new JSONParser().parse(this.responseBody);
    }

    public JSONArray getResponseBodyAsJSONArray() throws ParseException {
        return (JSONArray) new JSONParser().parse(this.responseBody);
    }

}