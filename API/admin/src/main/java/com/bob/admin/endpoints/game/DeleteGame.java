package com.bob.admin.endpoints.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteGame {
    @JsonProperty("gameID")
    public String gameID;
}