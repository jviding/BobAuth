package com.bob.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class SessionController {
    
    @RequestMapping("/login")
    public String login() {
        return "Logged in!";
    }

    @RequestMapping("/hasSession")
    public String hasSession() {
        return "true!";
    }

    @RequestMapping("/logout")
    public String logout() {
        return "Logged out!";
    }

}