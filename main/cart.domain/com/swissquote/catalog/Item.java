package com.swissquote.catalog;

import com.swissquote.utils.Jsonify;

public class Item {

    private final String name;

    public Item(String name) {
        this.name = name;
    }

    public String toJson() {
        return new Jsonify(name).toString();
    }
}