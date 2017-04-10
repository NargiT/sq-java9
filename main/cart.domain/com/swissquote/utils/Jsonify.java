package com.swissquote.utils;

/**
 * Created by tigran on 10/04/2017.
 */
public class Jsonify {

    private final String raw;

    public Jsonify(String name) {
        raw = name;
    }

    @Override
    public String toString() {
        return String.format("{ \"name\": \"%s\"}", raw);
    }
}
