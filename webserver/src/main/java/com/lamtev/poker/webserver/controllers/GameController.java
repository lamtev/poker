package com.lamtev.poker.webserver.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lamtev.poker.core.api.PlayerExpandedInfo;
import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.webserver.GameAPI;
import com.lamtev.poker.webserver.Room;
import com.lamtev.poker.webserver.controllers.exceptions.NoCardsException;
import com.lamtev.poker.webserver.controllers.exceptions.RoomStatusException;
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

    @GetMapping(value = "{roomId}/players", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<Map.Entry<String, PlayerExpandedInfo>> getPlayers(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "There isn't players info because game has not been started");
        return rooms.get(roomId).getGame().getPlayersInfo();
    }

    @GetMapping(value = "{roomId}/communityCards", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<Card> communityCards(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not get community cards because game has not been started");

        List<Card> communityCards = room.getGame().getCommunityCards();
        makeSureThatCardsAreNotEmpty(communityCards);
        return communityCards;
    }

    @GetMapping(value = "{roomId}/players/{playerId}/cards", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public List<Card> playerCards(@PathVariable String roomId,
                                  @PathVariable String playerId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room,
                "Can not get cards of player with roomId " + playerId + " because game has not been started");

        List<Card> playerCards = room.getGame().getPlayerCards(playerId);
        makeSureThatCardsAreNotEmpty(playerCards);
        return playerCards;
    }

    @GetMapping(value = "{roomId}/short-info", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(OK)
    public String shortInfo(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "There isn't info because game has not been started");
        GameAPI game = room.getGame();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("state", game.getCurrentStateName());
        jsonObject.addProperty("currentPlayerId", game.getCurrentPlayerId());
        jsonObject.addProperty("bank", game.getBank());
        jsonObject.addProperty("wager", game.getWager());
        return jsonObject.toString();
    }

    @PostMapping(value = "{roomId}/call")
    @ResponseStatus(ACCEPTED)
    public void call(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not call because game has not been started");
        room.getGame().call();
    }

    @PostMapping(value = "{roomId}/check")
    @ResponseStatus(ACCEPTED)
    public void check(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not check because game has not been started");
        room.getGame().check();
    }

    @PostMapping(value = "{roomId}/raise")
    @ResponseStatus(ACCEPTED)
    public void raise(@PathVariable String roomId,
                      @RequestParam(value = "additionalWager") int additionalWager) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not raise because game has not been started");
        room.getGame().raise(additionalWager);
    }

    @PostMapping(value = "{roomId}/allIn")
    @ResponseStatus(ACCEPTED)
    public void allIn(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not all-in because game has not been started");
        room.getGame().allIn();
    }

    @PostMapping(value = "{roomId}/fold")
    @ResponseStatus(ACCEPTED)
    public void fold(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not fold because game has not been started");
        room.getGame().fold();
    }

    @PostMapping(value = "{roomId}/showDown")
    @ResponseStatus(ACCEPTED)
    public void showDown(@PathVariable String roomId) throws Exception {
        makeSureThatRoomsExist();
        makeSureThatRoomExists(roomId);
        Room room = rooms.get(roomId);
        makeSureThatRoomIsTaken(room, "Can not show down because game has not been started");
        room.getGame().showDown();
    }

    private void makeSureThatCardsAreNotEmpty(List<Card> playerCards) throws NoCardsException {
        if (playerCards.isEmpty()) {
            throw new NoCardsException();
        }
    }

    private void makeSureThatRoomIsTaken(Room room, String message) throws RoomStatusException {
        if (room.isFree()) {
            throw new RoomStatusException("free", message);
        }
    }

}
