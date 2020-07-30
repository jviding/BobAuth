package com.bob.admin.endpoints.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteUser {
    @JsonProperty("userID")
    public int userID;
}