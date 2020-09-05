package com.bob.admin.lib.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;


public class Request extends RequestUrl {
    
    private String method;

    public Request(String method, String host, String endpoint) {
        super(host, endpoint);
        this.method = method;
    }

    public Response send() {
        try {
            HttpURLConnection con = (HttpURLConnection) super.getUrl().openConnection();
            con.setRequestMethod(this.method);
            return readResponse(con);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, "Something went wrong!");
        }
    }

    public Response send(Map<String,String> reqHeaders) {
        try {
            HttpURLConnection con = (HttpURLConnection) super.getUrl().openConnection();
            con.setRequestMethod(this.method);
            setRequestHeaders(con, reqHeaders);
            return readResponse(con);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, "Something went wrong!");
        }
    }

    public Response send(Map<String,String> reqHeaders, byte[] body) {
        try {
            HttpURLConnection con = (HttpURLConnection) super.getUrl().openConnection();
            con.setRequestMethod(this.method);
            setRequestHeaders(con, reqHeaders);
            sendBody(con, body);
            return readResponse(con);
        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, "Something went wrong!");
        }
    }

    private void setRequestHeaders(HttpURLConnection con, Map<String,String> reqHeaders) {
        for (Map.Entry<String,String> entry : reqHeaders.entrySet()) {
            con.setRequestProperty(entry.getKey(), entry.getValue());
        }
    }

    private void sendBody(HttpURLConnection con, byte[] body) throws IOException{
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(body, 0, body.length);
    }

    private Response readResponse(HttpURLConnection con) throws UnsupportedEncodingException, IOException, ParseException {
        int responseCode = con.getResponseCode();
        Map<String, List<String>> responseHeaders = con.getHeaderFields();
        String responseBody = readResponseBody(con);     
        return new Response(responseCode, responseBody, responseHeaders);
    }

    private String readResponseBody(HttpURLConnection con) throws UnsupportedEncodingException, IOException {
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
