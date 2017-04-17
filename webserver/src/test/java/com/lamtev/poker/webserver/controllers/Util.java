package com.lamtev.poker.webserver.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Util {

    private static String ROOM_JSON;

    public static String roomJson() {
        if (ROOM_JSON == null) {
            try {
                ROOM_JSON = new Scanner(
                        new File("src/test/resources/room.json"), "UTF-8"
                ).useDelimiter("\\Z").next();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return ROOM_JSON;
    }

}
