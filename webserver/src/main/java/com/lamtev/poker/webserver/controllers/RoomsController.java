package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.lamtev.poker.webserver.PokerGame;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
import com.lamtev.poker.webserver.controllers.exceptions.RequestBodyHasUnsuitableFormatException;
import com.lamtev.poker.webserver.controllers.exceptions.ResourceAlreadyExistsException;
import com.lamtev.poker.webserver.controllers.exceptions.RoomStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rooms")
public final class RoomsController extends AbstractController {

    @Autowired
    public RoomsController(Map<String, Room> rooms, Gson gson) {
        super(rooms, gson);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Collection<Room> rooms() throws Exception {
        makeSureThatRoomsExist();
        return rooms.values();
    }

    @GetMapping(value = "{roomId}", produces = APPLICATION_JSON_VALUE)
    public Room room(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        return rooms.get(roomId);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Room createRoom(@RequestBody Room room) throws Exception {
        makeSureThatRoomIsValid(room);
        makeSureThatRoomDoesNotExist(room);
        room.setGame(new PokerGame());
        rooms.put(room.getId(), room);
        return room;
    }

    @PutMapping(value = "{roomId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public Room updateRoom(@PathVariable String roomId,
                           @RequestBody Room newRoom) throws Exception {
        makeSureThatRoomIsValid(newRoom);
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsFree(room, "Can not update room");
        room.setId(newRoom.getId());
        room.setPlayersNumber(newRoom.getPlayersNumber());
        room.setStack(newRoom.getStack());
        return room;
    }

    @PostMapping(value = "{roomId}/start")
    @ResponseStatus(ACCEPTED)
    public void start(@PathVariable String roomId,
                      @RequestParam(value = "name") String name) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsFree(room, "PokerGame has been already started");
        GameAPI game = room.getGame();
        game.start(name, room.getPlayersNumber(), room.getStack());
        room.setFree(false);
    }

    private void makeSureThatRoomIsFree(Room room, String message) throws RoomStatusException {
        if (room.isTaken()) {
            throw new RoomStatusException("taken", message);
        }
    }

    private void makeSureThatRoomDoesNotExist(Room room) throws ResourceAlreadyExistsException {
        if (rooms.containsKey(room.getId())) {
            throw new ResourceAlreadyExistsException("Room with roomId " + room.getId());
        }
    }

    private void makeSureThatRoomIsValid(Room room) throws RequestBodyHasUnsuitableFormatException {
        if (room.hasUninitializedFields()) {
            throw new RequestBodyHasUnsuitableFormatException();
        }
    }

}
