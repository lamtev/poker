package com.lamtev.poker.android;


public class RoomInfo {

    private String name;
    private int playersNumber;
    private int stack;
    private boolean free;

    public RoomInfo(String name, int playersNumber, int stack, boolean free) {
        this.name = name;
        this.playersNumber = playersNumber;
        this.stack = stack;
        this.free = free;
    }

    public RoomInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
