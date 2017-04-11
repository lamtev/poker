package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.lamtev.poker.webserver.*;
import com.lamtev.poker.webserver.controllers.exceptions.RequestBodyHasUnsuitableFormatException;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("/rooms")
public final class RoomsController extends AbstractController {

    @Autowired
    public RoomsController(Map<String, Room> rooms, Gson gson) {
        super(rooms, gson);
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public Collection<Room> getRooms() {
        checkRoomsExistence();
        return rooms.values();
    }


    @RequestMapping(value = "{id}", method = GET, produces = APPLICATION_JSON_VALUE)
    public Room getRoom(@PathVariable String id) {
        checkRoomExistence(id);
        return rooms.get(id);
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Room createRoom(@RequestBody Room room) {
        if (room.hasUninitializedFields()) {
            throw new RequestBodyHasUnsuitableFormatException();
        }
        if (rooms.containsKey(room.getId())) {
            throw new ResourceAlreadyExistsException("Room with id " + room.getId());
        }
        room.setGame(new Game());
        rooms.put(room.getId(), room);
        return room;

    }

    @RequestMapping(value = "{id}", method = PUT, produces = APPLICATION_JSON_VALUE)
    public Room updateRoom(@PathVariable String id,
                           @RequestBody Room roomDiff) {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        checkRoomFreedom(room);
        if (roomDiff.getId() != null && !roomDiff.getId().isEmpty()) {
            room.setId(roomDiff.getId());
        }
        if (roomDiff.getPlayersNumber() > 0) {
            room.setPlayersNumber(roomDiff.getPlayersNumber());
        }
        if (roomDiff.getStack() > 0) {
            room.setStack(roomDiff.getStack());
        }
        return room;
    }

    @RequestMapping(value = "{id}/start", method = POST, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public Room start(@PathVariable String id,
                      @RequestParam(value = "name") String name) {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        checkRoomFreedom(room);
        GameAPI game = room.getGame();
        game.start(name, room.getPlayersNumber(), room.getStack());
        room.setFree(false);
        return room;
    }

}
