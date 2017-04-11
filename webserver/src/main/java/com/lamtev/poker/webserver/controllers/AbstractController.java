package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceNotFoundException;
import com.lamtev.poker.webserver.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public abstract class AbstractController {

    final Map<String, Room> rooms;
    final Gson gson;


    @Autowired
    public AbstractController(Map<String, Room> rooms, Gson gson) {
        this.rooms = rooms;
        this.gson = gson;
    }

    void checkRoomExistence(String id) {
        if (rooms == null || !rooms.containsKey(id)) {
            throw new ResourceNotFoundException("Room with id " + id);
        }
    }

    void checkRoomsExistence() {
        if (rooms == null || rooms.size() == 0) {
            throw new ResourceNotFoundException("Rooms");
        }
    }

    void checkRoomFreedom(Room room) {
        if (room.isNotFree()) {
            throw new IllegalStateException("Can't start game because it has already been started");
        }
    }

}
