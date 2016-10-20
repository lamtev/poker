package com.lamtev.poker.core;

//import org.testng.annotations.Test;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.atLeastOnce;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.testng.Assert.assertEquals;

public class PokerTest {

    //TODO functional tests
    @Test
    public void test() {
        Poker poker = new Poker(3, 10, 500);
        assertEquals(10, poker.getSmallBlindSize());
        assertEquals(2, poker.getCurrentPlayerIndex());
        poker.call();
        assertEquals(0, poker.getCurrentPlayerIndex());
        assertEquals(3, poker.getMoves());
        assertEquals(20, poker.getCurrentWager());
        assertEquals(GameStage.FIRST_WAGERING_LAP, poker.getCurrentGameStage());
        poker.raise(10);
        assertEquals(1, poker.getCurrentPlayerIndex());
        poker.raise(10);
        poker.raise(10);
        assertEquals(50, poker.getCurrentWager());
        poker.call();
        poker.call();
        assertEquals(GameStage.SECOND_WAGERING_LAP, poker.getCurrentGameStage());
    }

}
