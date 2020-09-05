package com.bob.admin.lib.http;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RequestUrl {
    
    private String url;
    private String queryParams;

    public RequestUrl(String host, String endpoint) {
        this.url = this.getBaseURL(host) + this.getPath(endpoint);
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

    public URL getUrl() throws MalformedURLException {
        return new URL(this.url + this.queryParams);
    }

    // PRIVATE

    private String getBaseURL(String host) throws IllegalArgumentException {
        switch(host) {
            case "iam":
                return "http://api-iam:8000";
            case "games":
                return "http://api-games:9000";
            default:
                throw new IllegalArgumentException("Unknown host.");
        }
    }

    private String getPath(String endpoint) throws IllegalArgumentException {
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
                throw new IllegalArgumentException("Unknown endpoint.");
        }
    }

}