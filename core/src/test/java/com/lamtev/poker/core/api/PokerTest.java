package com.lamtev.poker.core.api;

import com.lamtev.poker.core.util.PlayerInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests

    private List<PlayerInfo> generatePlayersInfo() {
        List<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo("a1", 100));
        playersInfo.add(new PlayerInfo("b1", 200));
        playersInfo.add(new PlayerInfo("c1", 300));
        return playersInfo;
    }

    @Test
    public void test() throws Exception {

        Poker poker = new Poker();
        assertEquals("SettingsPokerState", poker.getState().getClass().getSimpleName());
        poker.setUp(generatePlayersInfo(), 30);
        assertEquals("PreflopWageringPokerState", poker.getState().getClass().getSimpleName());

        poker.call();
        poker.call();

        assertEquals("FlopWageringPokerState", poker.getState().getClass().getSimpleName());
        assertEquals(3, poker.getCommonCards().size());

        poker.check();
        poker.check();
        poker.check();

        assertEquals("TurnWageringPokerState", poker.getState().getClass().getSimpleName());
        assertEquals(4, poker.getCommonCards().size());

        poker.check();
        poker.check();
        poker.check();

        assertEquals("RiverWageringPokerState", poker.getState().getClass().getSimpleName());
        assertEquals(5, poker.getCommonCards().size());

        poker.fold();
        poker.fold();

        assertEquals("GameIsOverPokerState", poker.getState().getClass().getSimpleName());

    }

}
