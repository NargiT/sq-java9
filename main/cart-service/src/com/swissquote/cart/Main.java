package com.swissquote.cart;

import com.sun.net.httpserver.HttpServer;
import com.swissquote.catalog.CartHandler;
import ping.VersionHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by tigran on 09/04/2017.
 */
public class Main {


    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        registerContexts(server);
        server.setExecutor(null);
        System.out.println("Start server localhost:8080");
        server.start();

    }

    private static void registerContexts(HttpServer server) {
        server.createContext("/version", new VersionHandler());
        server.createContext("/cart", new CartHandler());

    }

}
