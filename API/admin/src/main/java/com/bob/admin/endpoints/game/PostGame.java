package com.bob.admin.endpoints.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostGame {
    @JsonProperty("gameName")
    public String gameName;
}