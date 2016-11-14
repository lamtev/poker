package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Players implements Iterable<Player> {

    private List<Player> players = new ArrayList<>();

    public void add(Player player) {
        players.add(player);
    }

    public Player get(int index) {
        return players.get(index);
    }

    public Player get(String id) {
        for (Player player : players) {
            if (player.getId().equals(id)) {
                return player;
            }
        }
        throw new NullPointerException();
    }

    public int size() {
        return players.size();
    }

    public int activePlayersNumber() {
        int activePlayersNumber = 0;
        for (Player player : players) {
            if (player.isActive()) {
                activePlayersNumber++;
            }
        }
        return activePlayersNumber;
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    @Override
    public Iterator<Player> iterator() {
        return players.iterator();
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        players.forEach(action);
    }

}
