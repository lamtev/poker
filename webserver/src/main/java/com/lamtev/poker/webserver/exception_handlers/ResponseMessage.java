package com.lamtev.poker.webserver.exception_handlers;

public class ResponseMessage {

    private final int code;
    private final String message;

    public ResponseMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
