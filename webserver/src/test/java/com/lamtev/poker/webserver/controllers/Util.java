package com.lamtev.poker.webserver.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Util {

    private static String VALID_ROOM_JSON;
    private static String UPDATED_VALID_ROOM_JSON;
    private static String INVALID_ROOM_JSON;

    static String validRoomJson() {
        if (VALID_ROOM_JSON == null) {
            VALID_ROOM_JSON = roomJson("validRoom.json");
        }
        return VALID_ROOM_JSON;
    }

    static String updatedValidRoomJson() {
        if (UPDATED_VALID_ROOM_JSON == null) {
            UPDATED_VALID_ROOM_JSON = roomJson("updatedValidRoom.json");
        }
        return UPDATED_VALID_ROOM_JSON;
    }

    static String invalidRoomJson() {
        if (INVALID_ROOM_JSON == null) {
            INVALID_ROOM_JSON = roomJson("invalidRoom.json");
        }
        return INVALID_ROOM_JSON;
    }

    private static String roomJson(String name) {
        String roomJson = null;
        try {
            roomJson = new Scanner(
                    new File("src/test/resources/" + name), "UTF-8"
            ).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return roomJson;
    }

}
