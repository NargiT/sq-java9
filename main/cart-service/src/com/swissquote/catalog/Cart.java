package com.swissquote.catalog;

import java.util.*;

/**
 * Created by tigran on 09/04/2017.
 */
public class Cart {

    private final Set<Item> list;

    public Cart(Item... items) {
        this.list = new HashSet<>(Arrays.asList(items));
    }

    public Cart(Set<Item> list) {
        this.list = list;
    }

    public Cart() {
        this.list = new HashSet<>();
    }

    public Set<Item> items() {
        return list;
    }

    public void add(Item item) {
        list.add(item);
    }
}
