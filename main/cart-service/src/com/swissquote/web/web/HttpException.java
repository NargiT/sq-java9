package com.swissquote.web.web;

public class HttpException extends RuntimeException {
    public HttpException(String message) {
        super(message);
    }

    public HttpException(Exception e) {
        super(e);
    }
}
