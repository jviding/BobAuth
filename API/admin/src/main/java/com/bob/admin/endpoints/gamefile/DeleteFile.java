package com.bob.admin.endpoints.gamefile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteFile {
    @JsonProperty("gameID")
    public String gameID;
    @JsonProperty("type")
    public String type;
    @JsonProperty("filename")
    public String filename;
}