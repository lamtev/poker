package com.lamtev.poker.core.api;

import com.lamtev.poker.core.model.Card;
import com.lamtev.poker.core.model.CardDeck;
import com.lamtev.poker.core.model.Cards;
import com.lamtev.poker.core.model.Rank;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PokerTest implements StateChangedListener {

    //TODO functional tests

    private String state;

    private List<PlayerInfo> generatePlayersInfo() {
        List<PlayerInfo> playersInfo = new ArrayList<>();
        playersInfo.add(new PlayerInfo("a1", 100));
        playersInfo.add(new PlayerInfo("b1", 200));
        playersInfo.add(new PlayerInfo("c1", 300));
        return playersInfo;
    }

    @Test
    public void tst() {
        for (Rank r : Rank.values()) {
            System.out.println(r.ordinal());
        }
        Cards cardDeck = new CardDeck();
        for (Card c : cardDeck) {
            if (c.getRank().equals(Rank.ACE)) {
                System.out.println(c);
            }
        }
    }

    @Override
    public void changeState(String stateName) {
        this.state = stateName;
    }

    @Test
    public void test() throws Exception {

        Poker poker = new Poker();
        poker.addStateChangedListener(this);

        System.out.println(state);
        assertEquals("SettingsPokerState", poker.getState().getClass().getSimpleName());

        poker.setUp(generatePlayersInfo(), 30);
        System.out.println(state);
        assertEquals("PreflopWageringPokerState", poker.getState().getClass().getSimpleName());

        poker.call();
        poker.call();

        System.out.println(state);
        assertEquals("FlopWageringPokerState", poker.getState().getClass().getSimpleName());
        assertEquals(3, poker.getCommonCards().size());

        poker.check();
        poker.check();
        poker.check();

        System.out.println(state);
        assertEquals("TurnWageringPokerState", poker.getState().getClass().getSimpleName());
        assertEquals(4, poker.getCommonCards().size());

        poker.raise(40);
        poker.allIn();
        poker.call();

        System.out.println(state);
        assertEquals("ShowdownPokerState", poker.getState().getClass().getSimpleName());


        Cards cards = poker.showDown();
        poker.fold();
        poker.fold();

        System.out.println(state);
        System.out.println(poker.getCommonCards().toString() + cards);
        assertEquals("GameIsOverPokerState", poker.getState().getClass().getSimpleName());

        System.out.println(poker.getPlayersInfo().get(0).getStack());
        System.out.println(poker.getPlayersInfo().get(1).getStack());
        System.out.println(poker.getPlayersInfo().get(2).getStack());
    }

}
