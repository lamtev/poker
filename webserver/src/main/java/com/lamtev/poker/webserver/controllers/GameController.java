package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lamtev.poker.core.api.PlayerExpandedInfo;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rooms")
public class GameController extends AbstractController {

    @Autowired
    public GameController(Map<String, Room> rooms, Gson gson) {
        super(rooms, gson);
    }

    @RequestMapping(value = "{id}/players", method = GET, produces = APPLICATION_JSON_VALUE)
    public Map<String, PlayerExpandedInfo> getPlayers(@PathVariable String id) {
        return rooms.get(id).getGame().getPlayersInfo();
    }

    @RequestMapping(value = "{id}/short-info", method = GET, produces = APPLICATION_JSON_VALUE)
    public String getInfo(@PathVariable String id) {
        if (rooms == null || !rooms.containsKey(id)) {

        }
        Room room = rooms.get(id);
        GameAPI game = room.getGame();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", game.getCurrentStateName());
        jsonObject.addProperty("currentPlayerId", game.getCurrentPlayerId());
        jsonObject.addProperty("bank", game.getBank());
        return jsonObject.toString();
    }

    @RequestMapping(value = "{id}/call", method = POST, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(ACCEPTED)
    public void call(@PathVariable String id) throws Exception {
        checkRoomsExistence();
        checkRoomExistence(id);
        Room room = rooms.get(id);
        String player = room.getGame().getCurrentPlayerId();
        room.getGame().call();
        //TODO
    }

}
