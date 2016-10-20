package com.lamtev.poker.core;

import org.junit.Test;
import static org.junit.Assert.*;
import static com.lamtev.poker.core.GameStage.*;

public class GameStageTest {

    @Test
    public void testNext() {
        assertEquals(BLINDS, FOURTH_WAGERING_LAP.next());
        assertEquals(PREFLOP, BLINDS.next());
        assertEquals(FIRST_WAGERING_LAP, PREFLOP.next());
        assertEquals(FLOP, FIRST_WAGERING_LAP.next());
        assertEquals(SECOND_WAGERING_LAP, FLOP.next());
        assertEquals(TURN, SECOND_WAGERING_LAP.next());
        assertEquals(THIRD_WAGERING_LAP, TURN.next());
        assertEquals(RIVER, THIRD_WAGERING_LAP.next());
        assertEquals(FOURTH_WAGERING_LAP, RIVER.next());
    }

}
