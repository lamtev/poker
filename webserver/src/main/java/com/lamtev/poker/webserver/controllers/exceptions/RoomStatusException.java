package com.lamtev.poker.webserver.controllers.exceptions;

//TODO rename
public class RoomStatusException extends Exception {

    private static final String ROOM_IS = "Room is ";

    public RoomStatusException(String status, String message) {
        super(message + ". " + ROOM_IS + status);
    }

}
