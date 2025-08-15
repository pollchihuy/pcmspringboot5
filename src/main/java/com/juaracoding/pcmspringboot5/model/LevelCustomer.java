package com.juaracoding.pcmspringboot5.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LevelCustomer {

    private Long id;

    @JsonProperty("level_name")
    private String levelName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
