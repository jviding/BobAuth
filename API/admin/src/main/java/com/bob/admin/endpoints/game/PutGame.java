package com.bob.admin.endpoints.game;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.json.simple.JSONArray;

public class PutGame {
    @JsonProperty("gameID")
    public String gameID;
    @JsonProperty("newGameName")
    public String newGameName;
    @JsonProperty("removedResourceFiles")
    public JSONArray removedResourceFiles;
}