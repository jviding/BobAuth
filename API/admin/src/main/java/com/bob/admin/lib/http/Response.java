package com.bob.admin.lib.http;

import java.util.List;
import java.util.Map;

public class Response {
    
    private int responseCode;
    private Map<String, List<String>> responseHeaders;
    private String responseBody;

    public Response(int responseCode, Map<String, List<String>> responseHeaders, String responseBody) {
        this.responseCode = responseCode;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return this.responseHeaders;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public String getSetCookieHeader() {
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

}