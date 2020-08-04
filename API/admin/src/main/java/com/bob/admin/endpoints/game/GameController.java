package com.bob.admin.endpoints.game;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;
import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
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
            try {
                
                Request req = new Request("POST", "games", "game");
                
                req.addBodyParams("gameName", post.gameName);

                Response res = RequestService.send(req);

                response.setStatus(res.getResponseCode());

                return "{}";

            } catch (Exception e) {
                System.out.println(e);
            }            
        } else {
            response.setStatus(403);
            return "{}";
        }
        response.setStatus(404);
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
        if (AuthService.isAdmin(cookie)) {
            try {
                
                Request req = new Request("PUT", "games", "game");
                
                req.addBodyParams("gameID", put.gameID);
                req.addBodyParams("newGameName", put.newGameName);
                req.addBodyParams("removedResourceFiles", (JSONArray) put.removedResourceFiles);

                Response res = RequestService.send(req);

                response.setStatus(res.getResponseCode());

                return "{}";

            } catch (Exception e) {
                System.out.println(e);
            }            
        } else {
            response.setStatus(403);
            return "{}";
        }
        response.setStatus(404);
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
        if (AuthService.isAdmin(cookie)) {
            try {
                
                Request req = new Request("DELETE", "games", "game");
                
                req.addBodyParams("gameID", delete.gameID);

                Response res = RequestService.send(req);

                response.setStatus(res.getResponseCode());

                return "{}";

            } catch (Exception e) {
                System.out.println(e);
            }            
        } else {
            response.setStatus(403);
            return "{}";
        }
        response.setStatus(404);
        return "{}";
    }

}