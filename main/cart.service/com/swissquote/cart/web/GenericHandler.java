package com.swissquote.cart.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class GenericHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            switch (exchange.getRequestMethod().toLowerCase()) {
                case "post":
                    doPost(new HttpRequest(exchange), new HttpResponse(exchange));
                    break;
                case "get":
                    doGet(new HttpRequest(exchange), new HttpResponse(exchange));
                    break;
            }
        } catch (Throwable e) {
            System.err.println(e);
            new HttpResponse(exchange).error500(e);
        }
    }

    protected abstract void doGet(HttpRequest request, HttpResponse response) throws IOException;

    protected abstract void doPost(HttpRequest request, HttpResponse response) throws IOException;

}