package com.bob.admin.endpoints.users;

public class User {
 
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}