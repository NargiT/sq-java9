package com.swissquote.cart.handler;

import com.swissquote.cart.web.GenericHandler;
import com.swissquote.cart.web.HttpRequest;
import com.swissquote.cart.web.HttpResponse;
import com.swissquote.catalog.Cart;
import com.swissquote.catalog.Item;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * Created by tigran on 09/04/2017.
 */
public class CartHandler extends GenericHandler {

    private final Cart cart;

    public CartHandler() {
        cart = new Cart();
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        String collect = cart.items().stream().map(Item::toJson).collect(Collectors.joining(","));
        response.send("[" + collect + "]");
    }

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        System.out.println("New item added");
        addItems(request);
        response.send("OK");
    }

    private void addItems(HttpRequest request) {
        String params = getStringFromInputStream(request.getBody());
        String[] items = params.split("&");
        Arrays.asList(items).forEach(raw -> {
            String[] entry = raw.split("=");
            if ("item".equals(entry[0])) {
                String name = entry[1];
                System.out.println("Adding item " + name);
                cart.add(new Item(name));
            }
        });
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}