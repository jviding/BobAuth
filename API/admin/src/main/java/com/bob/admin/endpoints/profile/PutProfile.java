package com.bob.admin.endpoints.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutProfile {
    @JsonProperty("newEmail")
    public String newEmail;
    @JsonProperty("newPassword")
    public String newPassword;
    @JsonProperty("password")
    public String password;
}