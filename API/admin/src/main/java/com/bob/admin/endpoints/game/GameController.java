package com.bob.admin.endpoints.game;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    
    @GetMapping(
        value = "/game/{gameID}", 
        produces = "application/json"
    )
    public String getGame(
        @PathVariable String gameID, 
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        Request req = new Request("GET", "games", "game");
        req.addPathParam(gameID);
        Response res = req.send(cookie);
        if (res.getResponseCode() == 200) {
            return res.getResponseBody();
        } else {
            response.setStatus(400);
            return "{}";
        }
    }

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
        Request req = new Request("POST", "games", "game");
        req.addPathParam(post.gameName);
        Response res = req.send();
        if (res.getResponseCode() != 200) {
            response.setStatus(400);
        }
        return "{}";
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
        Request req = new Request("PUT", "games", "game");
        req.addPathParam(put.gameID);
        req.addPathParam(put.newName);
        Response res = req.send();
        if (res.getResponseCode() != 200) {
            response.setStatus(400);
        }
        return "{}";
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
        Request req = new Request("DELETE", "games", "game");
        req.addPathParam(delete.gameID);
        Response res = req.send();
        if (res.getResponseCode() != 200) {
            response.setStatus(400);
        }
        return "{}";           
    }

}