package com.swissquote.catalog;

public class Item {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String toJson() {
        return String.format("{ \"name\": \"%s\"}", name);
    }
}