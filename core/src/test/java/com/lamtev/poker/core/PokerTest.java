package com.lamtev.poker.core;


import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import org.testng.annotations.Test;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.testng.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() {
        Poker poker = new Poker(3, 10, 500);
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        assertEquals(2, poker.getCurrentPlayerIndex());
        poker.call();
        assertEquals(0, poker.getCurrentPlayerIndex());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        assertEquals(20, poker.getCurrentWager());
        poker.raise(10);
        assertEquals(1, poker.getCurrentPlayerIndex());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        assertEquals(30, poker.getCurrentWager());
        poker.raise(10);
        assertEquals(40, poker.getCurrentWager());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        poker.call();
        assertEquals(40, poker.getCurrentWager());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        poker.raise(50);
        assertEquals(90, poker.getCurrentWager());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        poker.call();
        assertEquals(90, poker.getCurrentWager());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        poker.call();
        assertEquals(90, poker.getCurrentWager());
        assertEquals(GameStage.SECOND_WAGERING_LAP, poker.getCurrentGameStage());
        poker.check();
        assertEquals(GameStage.SECOND_WAGERING_LAP, poker.getCurrentGameStage());
        poker.check();
        assertEquals(GameStage.SECOND_WAGERING_LAP, poker.getCurrentGameStage());
        poker.check();
        assertEquals(GameStage.SECOND_WAGERING_LAP, poker.getCurrentGameStage());
    }

}
