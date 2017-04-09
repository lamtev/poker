package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private static final String JSON = "application/json";
    private final Map<String, Room> rooms;
    private final Gson gson;

    @Autowired
    public RoomsController(Map<String, Room> rooms, Gson gson) {
        this.rooms = rooms;
        this.gson = gson;
    }

    @RequestMapping(method = GET, produces = JSON)
    public ResponseEntity<String> getRooms() {
        //FIXME
        if (rooms == null || rooms.size() == 0) {
            return new ResponseEntity<>("{}", NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(rooms.values()), OK);
    }

    @RequestMapping(value = "{id}", method = GET, produces = JSON)
    public ResponseEntity<String> getRoom(@PathVariable String id) {
        //FIXME
        if (rooms == null || !rooms.containsKey(id)) {
            return new ResponseEntity<>("{}", NO_CONTENT);
        }
        return new ResponseEntity<>(gson.toJson(rooms.get(id)), OK);

    }

    @RequestMapping(method = POST, consumes = JSON, produces = JSON)
    public ResponseEntity<String> createRoom(@RequestBody String body) {
        try {
            Room room = gson.fromJson(body, Room.class);
            if (rooms.containsKey(room.getId())) {
                return new ResponseEntity<>(CONFLICT);
            }
            room.setGame(new Game());
            rooms.put(room.getId(), room);
            return new ResponseEntity<>(body, OK);
        } catch (JsonSyntaxException e) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

    }

    @RequestMapping(value = "{id}", method = POST, consumes = JSON, produces = JSON)
    public ResponseEntity<String> updateRoom(@PathVariable String id,
                                             @RequestBody String body) {
        //TODO
        return new ResponseEntity<>(OK);
    }

    private class NameHolder {
        private String name;
    }

    @RequestMapping(value = "{id}/start", method = POST, produces = JSON)
    public ResponseEntity<String> start(@PathVariable String id,
                                        @RequestBody String body) {
        Room room = rooms.get(id);
        Game game = room.getGame();

        NameHolder nameHolder = gson.fromJson(body, NameHolder.class);
        game.start(nameHolder.name, room.getPlayersNumber(), room.getStack());
        return new ResponseEntity<>(OK);
    }

}
