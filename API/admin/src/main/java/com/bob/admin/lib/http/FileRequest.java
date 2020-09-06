package com.bob.admin.lib.http;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileRequest extends Request {
    
    private Map<String,String> requestHeaders = new HashMap<>();
    private MultipartFile file;

    public FileRequest(String method, String host, String endpoint) {
        super(method, host, endpoint);
        //this.requestHeaders.put("Content-Type", "application/json; charset=utf-8");
    }

    /*
    @Override
    public Response send() {
        // Send as multipart:
        // 1. file
        // 2. type, gameID, etc.
    }
    */

}