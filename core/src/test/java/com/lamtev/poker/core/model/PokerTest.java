package com.lamtev.poker.core.model;

import com.lamtev.poker.core.api.Poker;
import com.lamtev.poker.core.util.PlayerInfo;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() throws Exception {
        ArrayList<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo("a1", 100));
        playersInfo.add(new PlayerInfo("b1", 200));
        playersInfo.add(new PlayerInfo("c1", 300));

        playersInfo.forEach(playerInfo -> System.out.println(playerInfo.getId() + " " + playerInfo.getStack()));

        Poker poker = new Poker();

        poker.start(playersInfo, 20);

        System.out.println(poker.getState().getClass());

        poker.getPlayersInfo().forEach(playerInfo -> System.out.println(playerInfo.getId() + " " + playerInfo.getStack()));

        assertEquals(2, poker.getPlayerCards("a1").size());
        assertEquals(2, poker.getPlayerCards("b1").size());
        assertEquals(2, poker.getPlayerCards("c1").size());

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
    }

}
