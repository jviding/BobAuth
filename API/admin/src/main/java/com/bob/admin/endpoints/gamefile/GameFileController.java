package com.bob.admin.endpoints.gamefile;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.FileRequest;
import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class GameFileController {

    @PostMapping(
        value = "/gamefile", 
        consumes = "multipart/form-data",
        produces = "application/json"
    )
    public String uploadGameFile(
        @RequestPart(value="gameID") String gameID,    
        @RequestPart(value="type") String type,
        @RequestPart(value="file") MultipartFile file,
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        FileRequest req = new FileRequest("POST", "files", "");
        req.addPathParam(gameID);
        req.addPathParam(type);
        req.setFile(file);
        Response res = req.send();
        if (res.getResponseCode() != 200) {
            response.setStatus(400); 
        }
        return "{}";
    }

    @DeleteMapping(
        value = "/gamefile",
        produces = "application/json"
    )
    public String deleteGameFile(
        @RequestBody DeleteFile delete, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        Request req = new Request("DELETE", "files", "");
        req.addPathParam(delete.gameID);
        req.addPathParam(delete.type);
        req.addPathParam(delete.filename);
        Response res = req.send();
        if (res.getResponseCode() != 200) {
            response.setStatus(400);
        }
        return "{}";
    }

}