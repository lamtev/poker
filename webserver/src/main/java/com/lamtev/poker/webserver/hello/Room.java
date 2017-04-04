package com.lamtev.poker.webserver.hello;

public class Room {

    private String id;
    private int playersNumber;
    private long stack;
    private boolean isFree = true;

    public Room(String id, int playersNumber, long stack) {
        this.id = id;
        this.playersNumber = playersNumber;
        this.stack = stack;
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

    public long getStack() {
        return stack;
    }

    public void setStack(long stack) {
        this.stack = stack;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
