package com.ua.alevel.jsonCrud.cfg;

import lombok.Getter;

@Getter
public enum FileType {
    JSON_TYPE("users.json");

    private final String path;

    FileType(String path) {
        this.path = path;
    }
}
