package com.swissquote.cart.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by tigran on 09/04/2017.
 */
public class VersionHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String jdk = "jdk: " + System.getProperty("java.version");
        exchange.sendResponseHeaders(200, jdk.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(jdk.getBytes());
        exchange.close();
    }
}
