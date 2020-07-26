package com.bob.admin.endpoints.users;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    @GetMapping("/users")
    public String getUsers() {
        return "Hello";
    }

    // promoteUser -> at updateUser
    // demoteUser  -> at updateUser

    // createUser   POST    path = /user
    // readUser     GET     path = /user/{id}
    // readUsers    GET     path = /users
    // updateUser   PUT     path = /user/{id}
    // deleteUser   DELETE  path = /user/{id}

}