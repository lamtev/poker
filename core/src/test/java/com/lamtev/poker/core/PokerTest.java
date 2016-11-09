package com.lamtev.poker.core;


import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

import static org.junit.Assert.assertEquals;
//import org.testng.annotations.Test;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.testng.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() throws Exception {
        Map<String, Integer> playersInfo = new LinkedHashMap<>();
        playersInfo.put("a", 1);

        playersInfo.put("c", 3);
        playersInfo.put("b", 2);

        List<Player> playersList = new ArrayList<>();
        playersInfo.forEach((key, value) -> {
            Player player = new Player(key, value);
            playersList.add(player);
        });

        for (Player player : playersList) {
            System.out.println(player.getId() + player.getStack());
        }
    }

}
