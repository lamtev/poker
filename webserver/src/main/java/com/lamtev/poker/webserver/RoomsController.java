package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final Map<String, Room> rooms;
    private final Gson gson;

    @Autowired
    public RoomsController(Map<String, Room> rooms, Gson gson) {
        this.rooms = rooms;
        this.gson = gson;
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<Room> getRooms() {
        if (rooms == null || rooms.size() == 0) {
            throw new ResourceNotFoundException("Rooms");
        }
        return rooms.values();
    }

    @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public Room getRoom(@PathVariable String id) {
        if (rooms == null || !rooms.containsKey(id)) {
            throw new ResourceNotFoundException("Room with " + id);
        }
        return rooms.get(id);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRoom(@RequestBody String body) {
        try {
            Room room = gson.fromJson(body, Room.class);
            if (rooms.containsKey(room.getId())) {
                return new ResponseEntity<>(
                        new Error("Room with the same id already exists"),
                        CONFLICT
                );
            }
            room.setGame(new Game());
            rooms.put(room.getId(), room);
            return new ResponseEntity<>(body, CREATED);
        } catch (JsonSyntaxException e) {
            return new ResponseEntity<>(
                    new Error("Invalid json syntax: " + e.getMessage()),
                    BAD_REQUEST
            );
        }

    }

    @RequestMapping(value = "{id}", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateRoom(@PathVariable String id,
                                             @RequestBody String body) {
        //TODO if not free -> no access to update and visit
        return new ResponseEntity<>(OK);
    }

    @RequestMapping(value = "{id}/start", method = POST, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> start(@PathVariable String id,
                                   @RequestParam(value = "name") String name) {
        Room room = rooms.get(id);
        if (!room.isFree()) {
            return new ResponseEntity<>(new Error("Room is not free"), CONFLICT);
        }
        GameAPI game = room.getGame();
        game.start(name, room.getPlayersNumber(), room.getStack());
        room.setFree(false);
        return new ResponseEntity<>(room, ACCEPTED);
    }

}
