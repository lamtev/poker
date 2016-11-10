package com.lamtev.poker.core;


import org.junit.Test;

import java.util.*;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() throws Exception {
        LinkedHashMap<String, Integer> playersInfo = new LinkedHashMap<>();
        playersInfo.put("a1", 100);
        playersInfo.put("b1", 200);
        playersInfo.put("c1", 300);

        PokerAPI poker = new Poker();
        poker.start(playersInfo, 200, "a1");
    }

}
