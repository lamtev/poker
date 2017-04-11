package com.lamtev.poker.webserver;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties("game")
public class Room {

    private final String id;
    private int playersNumber;
    private int stack;
    private boolean free;

    private GameAPI game = new Game();

    public Room(String id, int playersNumber, int stack, boolean free) {

        this.id = id;
        this.playersNumber = playersNumber;
        this.stack = stack;
        this.free = free;
    }

    public String getId() {
        return id;
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

    public GameAPI getGame() {
        return game;
    }

    public void setGame(GameAPI game) {
        this.game = game;
    }

}
