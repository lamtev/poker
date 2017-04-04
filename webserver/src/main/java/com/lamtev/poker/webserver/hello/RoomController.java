package com.lamtev.poker.webserver.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RoomController {

    private static Map<String, Room> rooms = new HashMap<>();

    static {
        rooms.put("1", new Room("1", 7, 50000));
        rooms.put("2", new Room("2", 4, 10000));
    }

    @GetMapping("/rooms")
    public Room getRoom(@RequestParam(value = "id", defaultValue = "1") String id) {
        return rooms.get(id);
    }

}
