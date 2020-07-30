package com.bob.admin.endpoints.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutUser {
    @JsonProperty("userID")
    public int userID;
    @JsonProperty("email")
    public String email;
    @JsonProperty("isAdmin")
    public boolean isAdmin;
}