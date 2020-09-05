package com.bob.admin.endpoints.game;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;
import com.bob.admin.lib.http.JsonRequest;
import com.bob.admin.lib.http.Response;

import org.json.simple.JSONArray;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    
    @PostMapping(
        value = "/game", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String createGame(
        @RequestBody PostGame post, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        if (AuthService.isAdmin(cookie)) {  
            JsonRequest req = new JsonRequest("POST", "games", "game");
            req.addBodyParams("gameName", post.gameName);
            Response res = req.send();
            response.setStatus(res.getResponseCode());
            return "{}";
        } else {
            response.setStatus(403);
            return "{}";
        }
    }

    @PutMapping(
        value = "/game", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String updateGame(
        @RequestBody PutGame put,
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        if (AuthService.isAdmin(cookie)) {
            JsonRequest req = new JsonRequest("PUT", "games", "game");
            req.addBodyParams("gameID", put.gameID);
            req.addBodyParams("newGameName", put.newGameName);
            req.addBodyParams("removedResourceFiles", (JSONArray) put.removedResourceFiles);
            Response res = req.send();
            response.setStatus(res.getResponseCode());
            return "{}";
        } else {
            response.setStatus(403);
            return "{}";
        }
    }

    @DeleteMapping(
        value = "/game", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String deleteGame(
        @RequestBody DeleteGame delete,
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        if (AuthService.isAdmin(cookie)) {   
            JsonRequest req = new JsonRequest("DELETE", "games", "game");
            req.addBodyParams("gameID", delete.gameID);
            Response res = req.send();
            response.setStatus(res.getResponseCode());
            return "{}";           
        } else {
            response.setStatus(403);
            return "{}";
        }
    }

}