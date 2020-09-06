package com.bob.admin.endpoints.gamefile;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
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
        @RequestPart(value="fileType") String fileType,
        @RequestPart(value="file") MultipartFile file,
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        if (AuthService.isAdmin(cookie)) {
                
            //Request req = new Request("POST", "games", "game");
            
            //req.addBodyParams("gameName", post.gameName);

            //Response res = RequestService.send(req);

            //response.setStatus(res.getResponseCode());

            System.out.println(fileType);
            System.out.println(file.getOriginalFilename());
            //System.out.println(file.getBytes());

            return "{}";
           
        } else {
            response.setStatus(403);
            return "{}";
        }
    }

}