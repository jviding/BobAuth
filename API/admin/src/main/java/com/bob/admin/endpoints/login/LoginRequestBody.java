package com.bob.admin.endpoints.login;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequestBody {
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;
}