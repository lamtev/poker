package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lamtev.poker.core.api.PlayerExpandedInfo;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
import com.lamtev.poker.webserver.controllers.exceptions.RoomStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rooms")
public class GameController extends AbstractController {

    @Autowired
    public GameController(Map<String, Room> rooms, Gson gson) {
        super(rooms, gson);
    }

    @GetMapping(value = "{id}/players", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<Map.Entry<String, PlayerExpandedInfo>> getPlayers(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "There isn't players info because game has not been started");
        return rooms.get(id).getGame().getPlayersInfo();
    }

    @GetMapping(value = "{id}/short-info", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public String shortInfo(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "There isn't info because game has not been started");
        GameAPI game = room.getGame();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", game.getCurrentStateName());
        jsonObject.addProperty("currentPlayerId", game.getCurrentPlayerId());
        jsonObject.addProperty("bank", game.getBank());
        return jsonObject.toString();
    }

    @PostMapping(value = "{id}/call")
    @ResponseStatus(ACCEPTED)
    public void call(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "Can not call because game has not been started");
        room.getGame().call();
    }

    @PostMapping(value = "{id}/check")
    @ResponseStatus(ACCEPTED)
    public void check(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "Can not check because game has not been started");
        room.getGame().check();
    }

    @PostMapping(value = "{id}/raise")
    @ResponseStatus(ACCEPTED)
    public void raise(@PathVariable String id,
                      @RequestParam(value = "additionalWager") int additionalWager) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "Can not raise because game has not been started");
        room.getGame().raise(additionalWager);
    }

    @PostMapping(value = "{id}/fold")
    @ResponseStatus(ACCEPTED)
    public void fold(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "Can not fold because game has not been started");
        room.getGame().fold();
    }

    @PostMapping(value = "{id}/showDown")
    @ResponseStatus(ACCEPTED)
    public void showDown(@PathVariable String id) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(id);
        Room room = rooms.get(id);
        makeSureThatRoomIsTaken(room, "Can not show down because game has not been started");
        room.getGame().showDown();
    }

    private void makeSureThatRoomIsTaken(Room room, String message) throws RoomStateException {
        if (room.isFree()) {
            throw new RoomStateException("free", message);
        }
    }

}
