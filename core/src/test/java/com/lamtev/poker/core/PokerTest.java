package com.lamtev.poker.core;


import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

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
        Map<String, Player> players = new LinkedHashMap<String, Player>() {{
            put("Vasya", new Player(100));
            put("Petya", new Player(200));
            put("Vanya", new Player(300));
            put("Anya", new Player(100));
            put("Masha", new Player(100));
            put("Vika", new Player(400));
        }};
    }

}
