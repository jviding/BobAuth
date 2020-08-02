package com.bob.admin.endpoints.games;

import javax.servlet.http.HttpServletResponse;

import com.bob.admin.lib.AuthService;
import com.bob.admin.lib.http.Request;
import com.bob.admin.lib.http.RequestService;
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
            try {
                
                Request req = new Request("GET", "games", "games");
                
                Response res = RequestService.send(req);

                response.setStatus(res.getResponseCode());

                if (res.getResponseCode() == 200) {
                    return res.getResponseBody();
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            response.setStatus(404);
            return "{}";
        } else {
            response.setStatus(403);
            return "{}";
        }
    }
}