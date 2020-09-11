package com.bob.admin.lib.http;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileRequest extends Request {
    
    private static final String LINE_FEED = "\r\n";
    private static final String BOUNDARY = "--BobBoundary" + Long.toHexString(System.currentTimeMillis());
    
    private Map<String,String> requestHeaders = new HashMap<>();
    private Map<String,String> formFields = new HashMap<>();
    private MultipartFile file;

    public FileRequest(String method, String host, String endpoint) {
        super(method, host, endpoint);
        this.requestHeaders.put("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
    }

    public boolean addFormField(String name, String value) {
        String regex = "^[a-zA-Z0-9]*$";
        boolean isValidName = name.matches(regex);
        boolean isValidValue = value.matches(regex);
        if (isValidName && isValidValue) {
            this.formFields.put(name, value);
            return true;
        } else {
            return false;
        }
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public Response send(String sessionCookie) {
        this.requestHeaders.put("Cookie", "sessionid=" + sessionCookie);
        return send();
    }

    @Override
    public Response send() {
        try {
            byte[] body = getBody();
              
            //import java.nio.charset.StandardCharsets;
            //String b = new String(body, StandardCharsets.US_ASCII);
            //System.out.println(b);

            //return new Response(500, "Something went wrong!");
            return super.send(this.requestHeaders, getBody());
        } catch (Exception e) {
            System.out.println(e);
            return new Response(500, "Something went wrong!");
        }
    }


    // PRIVATE

    private String getSecureFilenameHex(String filename) {
        return DigestUtils.md5DigestAsHex(filename.getBytes());
    }

    private String getSecureFileNameSuffix(String filename) {
        String[] splitString = filename.split("\\.");
        String suffix = splitString[splitString.length - 1];
        if (splitString.length > 1 && suffix.matches("^[a-zA-Z]*$")) {
            return suffix;
        } else {
            throw new IllegalArgumentException("Invalid or missing filename suffix!");
        }
    }

    private String getSecureContentType(MultipartFile file) {
        String contentType = this.file.getContentType();
        if (contentType.matches("^[a-zA-Z\\-/]*$")) {
            return contentType;
        } else {
            throw new IllegalArgumentException("Invalid content type!");
        }
    }

    private byte[] combineBytes(byte[][] byteArr) {
        int len = 0;
        for (byte[] b : byteArr) {
            len += b.length;
        }
        byte[] allBytes = new byte[len];
        ByteBuffer buff = ByteBuffer.wrap(allBytes);
        for (byte[] b : byteArr) {
            buff.put(b);
        }
        return buff.array();
    }

    private byte[] getFormFields() {
        String fields = "";
        for (Map.Entry<String,String> entry : this.formFields.entrySet()) {
            fields += "--" + BOUNDARY + LINE_FEED;
            fields += "Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_FEED;
            fields += "Content-Type: text/plain; charset=utf-8" + LINE_FEED;
            fields += LINE_FEED;
            fields += entry.getValue() + LINE_FEED;
        }
        return fields.getBytes();
    }

    private byte[] getFileField() throws IOException {
        if (this.file != null && this.file.getSize() > 0) {
            String filename = getSecureFilenameHex(this.file.getOriginalFilename());
            String suffix = getSecureFileNameSuffix(this.file.getOriginalFilename());
            String contentType = getSecureContentType(this.file);      

            String lines = "";
            lines += "--" + BOUNDARY + LINE_FEED;
            lines += "Content-Disposition: form-data; name=\"file\"; filename=\"" + filename + "." + suffix + "\"" + LINE_FEED;
            lines += "Content-Type: \"" + contentType + "\"; charset=utf-8" + LINE_FEED;
            lines += LINE_FEED;
    
            byte[][] byteArr = {lines.getBytes(), this.file.getBytes()};
            return combineBytes(byteArr);
        } else {
            return "".getBytes();
        }
    }

    private byte[] getBody() throws IOException {
        byte[] lastBoundary = ("--" + BOUNDARY + "--" + LINE_FEED).getBytes();
        byte[][] byteArr = { getFormFields(), getFileField(), lastBoundary };
        return combineBytes(byteArr);
    }
}