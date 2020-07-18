package com.bob.admin.endpoints.login;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginController {
    
    @PostMapping(
        value = "/login", 
        consumes = "application/json",
        produces = "application/json"
    )
    public String login(@RequestBody LoginRequestBody login) {

        System.out.println(login.username);
        System.out.println(login.password);

        return "Logged in!";
    }

}

/*
const REQ_OPTIONS = {
        method: 'POST',
        host: 'iam',
        endpoint: 'login',
        urlParams: false,
        payload: {
            username: req.body.username,
            password: req.body.password
        }
    }

    return sendRequest(req, REQ_OPTIONS)
        .then((res) => {
            if (res.statusCode === 200) {
                const COOKIES = res.headers['set-cookie']
                return Promise.resolve(COOKIES)
            } else {
                return Promise.reject()
            }
        })
*/