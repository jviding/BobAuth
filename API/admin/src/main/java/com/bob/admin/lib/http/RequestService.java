package com.bob.admin.lib.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;


public class RequestService {

    public static Response send(Request req) {
        try {
            HttpURLConnection con = (HttpURLConnection) req.getUrl().openConnection();
            con.setRequestMethod(req.getMethod());

            setSessionCookieIfExists(req, con);
            sendRequestBodyIfExists(req, con);

            final int RESPONSE_CODE = con.getResponseCode();
            final Map<String, List<String>> RESPONSE_HEADERS = con.getHeaderFields();
            final String RESPONSE_BODY = readResponseBody(con);

            return new Response(RESPONSE_CODE, RESPONSE_HEADERS, RESPONSE_BODY);

        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, null, "Something went wrong!");
        }
    }

    private static void setSessionCookieIfExists(Request req, HttpURLConnection con) {
        con.setRequestProperty("Cookie", "sessionid=" + req.getSessionCookie());
    }

    private static void sendRequestBodyIfExists(Request req, HttpURLConnection con) throws IOException {
        if (req.hasBody()) {
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);
            OutputStream os = con.getOutputStream();
            byte[] body = req.getBody().getBytes("utf-8");
            os.write(body, 0, body.length);
        }
    }

    private static String readResponseBody(HttpURLConnection con) throws Exception {
        InputStream is;
        try {
            is = con.getInputStream();
        } catch (Exception e) {
            is = con.getErrorStream();
        }
        InputStreamReader isr = new InputStreamReader(is, "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response.toString();
    }
}