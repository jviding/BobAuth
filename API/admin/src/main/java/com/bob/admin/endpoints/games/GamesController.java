package com.bob.admin.endpoints.games;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;
import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.Response;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GamesController {

    @GetMapping(
        value = "/games", 
        produces = "application/json"
    )
    public String getGames(
        @CookieValue(name = "sessionid", defaultValue = "") String cookie,
        HttpServletResponse response
    ) {
        if (AuthService.isAdmin(cookie)) {     
            Request req = new Request("GET", "games", "games");
            Response res = req.send();
            response.setStatus(res.getResponseCode());
            if (res.getResponseCode() == 200) {
                return res.getResponseBody();
            } else {
                return "{}";
            }
        } else {
            response.setStatus(403);
            return "{}";
        }
    }
}