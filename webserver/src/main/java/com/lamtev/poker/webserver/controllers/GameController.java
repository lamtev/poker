package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lamtev.poker.core.api.PlayerExpandedInfo;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Map<String, PlayerExpandedInfo> getPlayers(@PathVariable String id) {
        checkRoomsExistence();
        checkRoomExistence(id);
        return rooms.get(id).getGame().getPlayersInfo();
    }

    @GetMapping(value = "{id}/short-info", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public String getInfo(@PathVariable String id) {
        //TODO
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        GameAPI game = room.getGame();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", game.getCurrentStateName());
        jsonObject.addProperty("currentPlayerId", game.getCurrentPlayerId());
        jsonObject.addProperty("bank", game.getBank());
        return jsonObject.toString();
    }

    @PostMapping(value = "{id}/call", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public void call(@PathVariable String id) throws Exception {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        room.getGame().call();
    }

    @PostMapping(value = "{id}/check", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public void check(@PathVariable String id) throws Exception {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        room.getGame().check();
    }

    @PostMapping(value = "{id}/raise", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public void raise(@PathVariable String id,
                      @RequestParam(value = "additionalWager") int additionalWager) throws Exception {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        room.getGame().raise(additionalWager);
    }

}
