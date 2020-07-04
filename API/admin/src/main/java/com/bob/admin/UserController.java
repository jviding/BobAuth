package com.bob.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class UserController {
    
    @RequestMapping("/getUsers")
    public String getUsers() {
        return "Users list!";
    }

    // promoteUser
    // demoteUser

    // addUser
    // removeUser

}