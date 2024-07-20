package org.linkedin.utils;

public class Response {
    private final boolean result;
    private final String message;
    private int code;
    private String token;
    private Object object;
    public Response(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public Response(boolean result, String message, int code, Object object) {
        this.result = result;
        this.message = message;
        this.code = code;
        this.object = object;
    }

    public Response(boolean result, String message, int code) {
        this.result = result;
        this.message = message;
        this.code = code;
    }

    public Response(boolean result, String message, int code, String token) {
        this.result = result;
        this.message = message;
        this.code = code;
        this.token = token;
    }

    public Response(boolean result, String message, Object object) {
        this.result = result;
        this.message = message;
        this.object = object;
    }

    public boolean is() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getToken() {
        return token;
    }
}
