package com.pastebin.pasterbin.entity;

public enum ContentType {
    TXT("texts"),
    IMG("images"),
    VID("videos"),
    DOC("documents");

    private final String key;

    ContentType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}

