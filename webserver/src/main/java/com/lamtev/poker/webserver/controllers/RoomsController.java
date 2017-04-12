package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.lamtev.poker.webserver.Game;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
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
        checkRoomsExistence();
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

    @RequestMapping(value = "{id}", method = PUT, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Room updateRoom(@PathVariable String id,
                           @RequestBody Room newRoom) {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        checkRoomFreedom(room);
        String newId = newRoom.getId();
        int newPlayersNumber = newRoom.getPlayersNumber();
        int newStack = newRoom.getStack();
        if (newId != null && !newId.isEmpty() && newPlayersNumber > 1 && newStack > 1) {
            room.setId(newId);
            room.setPlayersNumber(newPlayersNumber);
            room.setStack(newStack);
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
