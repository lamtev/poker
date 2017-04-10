package com.lamtev.poker.webserver;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.lamtev.poker.core.api.PlayerExpandedInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/rooms")
public class GameController {

    private final Map<String, Room> rooms;
    private final Gson gson;

    @Autowired
    public GameController(Map<String, Room> rooms, Gson gson) {
        this.rooms = rooms;
        this.gson = gson;
    }

    @RequestMapping(value = "{id}/players", method = GET, produces = APPLICATION_JSON_VALUE)
    public Map<String, PlayerExpandedInfo> getPlayers(@PathVariable String id) {
        return rooms.get(id).getGame().getPlayersInfo();
    }

    @RequestMapping(value = "{id}/get-info", method = POST, produces = APPLICATION_JSON_VALUE)
    public String getInfo(@PathVariable String id) {
        Room room = rooms.get(id);
        if (room == null) {

        }
        Game game = room.getGame();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", game.getCurrentStateName());
        jsonObject.addProperty("currentPlayerId", game.getCurrentPlayerId());
        jsonObject.addProperty("bank", game.getBank());
        return jsonObject.toString();
    }

}
