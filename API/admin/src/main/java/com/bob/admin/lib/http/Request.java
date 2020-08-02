package com.bob.admin.lib.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Request {
    
    private String method;
    private String url;
    private String sessionCookie;
    private String queryParams;
    private JSONObject requestBody;

    /**
     * @param method    -> GET | POST | PUT | DELETE
     * @param host      -> iam | games
     * @param endpoint  -> login | logout | ...
     * @param sessionCookie
     */
    public Request(String method, String host, String endpoint, String sessionCookie) throws Exception {
        this(method, host, endpoint);
        this.sessionCookie = sessionCookie;
    }
    public Request(String method, String host, String endpoint) throws Exception {
        this.method = method;
        this.url = "http://" + this.getHostname(host) + ":" + this.getPort(host) + this.getPath(endpoint);
        this.queryParams = "";
        this.requestBody = new JSONObject();
    }

    // PRIVATE

    private String getHostname(String host) throws Exception {
        switch(host) {
            case "iam":
                return "api-iam";
            case "games":
                return "api-games";
            default:
                throw new Exception("Unknown host.");
        }
    }

    private String getPath(String endpoint) throws Exception {
        switch(endpoint) {
            case "login":
                return "/login";
            case "logout":
                return "/logout";
            case "profile":
                return "/profile";
            case "users":
                return "/users";
            case "user":
                return "/user";
            case "game":
                return "/game";
            case "gameFile":
                return "/game/file";
            case "games":
                return "/games";
            default:
                throw new Exception("Unknown path.");
        }
    }

    private String getPort(String host) throws Exception {
        switch(host) {
            case "iam":
                return "8000";
            case "games":
                return "9000";
            default:
                throw new Exception("Unknown host.");
        }
    }

    // PUBLIC

    public String getMethod() {
        return this.method;
    }

    public URL getUrl() throws MalformedURLException {
        return new URL(this.url + this.queryParams);
    }

    public String getSessionCookie() throws Exception {
        if (this.sessionCookie != null) {
            return this.sessionCookie;
        } else {
            throw new Exception("No session cookie found!");
        }
    }

    public void addQueryParams(String key, String value) throws UnsupportedEncodingException {
        final String URL_SAFE_KEY = URLEncoder.encode(key, "utf-8");
        final String URL_SAFE_VALUE = URLEncoder.encode(value, "utf-8");
        if (this.queryParams.length() == 0) {
            this.queryParams += "?" + URL_SAFE_KEY + "=" + URL_SAFE_VALUE;
        } else {
            this.queryParams += "&" + URL_SAFE_KEY + "=" + URL_SAFE_VALUE;
        }
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

    public boolean hasBody() {
        return !this.requestBody.isEmpty();
    }

    public String getBody() {
        return this.requestBody.toJSONString();
    }

}
