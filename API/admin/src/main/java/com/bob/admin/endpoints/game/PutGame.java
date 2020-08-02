package com.bob.admin.endpoints.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PutGame {
    @JsonProperty("gameID")
    public String gameID;
    @JsonProperty("newGameName")
    public String newGameName;
}