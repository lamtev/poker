package com.lamtev.poker.webserver.controllers.exceptions;

public class RoomStateException extends Exception {

    private static final String ROOM_IS = "Room is ";

    public RoomStateException(String state, String message) {
        super(message + ". " + ROOM_IS + state);
    }

}
