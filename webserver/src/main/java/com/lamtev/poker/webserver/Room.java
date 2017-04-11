package com.lamtev.poker.webserver;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Room {

    private String id;
    private int playersNumber;
    private int stack;
    private boolean free;
    @JsonIgnore
    private GameAPI game = new Game();

    public Room() {

    }

    public Room(String id, int playersNumber, int stack, boolean free) {
        this.id = id;
        this.playersNumber = playersNumber;
        this.stack = stack;
        this.free = free;
    }

    public boolean hasUninitializedFields() {
        return !isValid() || !free;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public int getStack() {
        return stack;
    }

    public void setStack(int stack) {
        this.stack = stack;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    @JsonIgnore
    public boolean isValid() {
        return id != null && !id.isEmpty() && playersNumber > 1 && stack > 0;
    }

    @JsonIgnore
    public boolean isNotFree() {
        return !free;
    }

    public GameAPI getGame() {
        return game;
    }

    public void setGame(GameAPI game) {
        this.game = game;
    }

}
