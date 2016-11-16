package com.lamtev.poker.core.api;

import com.lamtev.poker.core.util.PlayerInfo;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() throws Exception {
        ArrayList<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo("a1", 100));
        playersInfo.add(new PlayerInfo("b1", 200));
        playersInfo.add(new PlayerInfo("c1", 300));

        Poker poker = new Poker(playersInfo, 20);

        System.out.println(poker.getState().getClass());

        assertEquals(0, poker.getCommonCards().size());

        poker.call();
        poker.call();
        poker.check();
        poker.check();
        poker.check();

        System.out.println(poker.getState().getClass());
        assertEquals(3, poker.getCommonCards().size());

        poker.check();
        poker.check();
        poker.check();

        System.out.println(poker.getState().getClass());
        assertEquals(4, poker.getCommonCards().size());

        poker.check();
        poker.check();
        poker.check();

        System.out.println(poker.getState().getClass());
        assertEquals(5, poker.getCommonCards().size());

        poker.fold();
        poker.fold();

        System.out.println(poker.getState().getClass());

    }

}
