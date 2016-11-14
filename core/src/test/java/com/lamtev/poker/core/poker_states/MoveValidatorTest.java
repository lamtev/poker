package com.lamtev.poker.core.poker_states;

import com.lamtev.poker.core.Bank;
import com.lamtev.poker.core.Player;
import com.lamtev.poker.core.Players;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MoveValidatorTest {

    private Players generatePlayers() {
        return new Players() {{
            add(new Player("a", 200));
            add(new Player("b", 200));
            add(new Player("c", 200));
            add(new Player("d", 200));
            add(new Player("e", 200));
        }};
    }

    @Test(expected = Exception.class)
    public void validateCall() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateCall(0);
    }

    @Test(expected = Exception.class)
    public void validateRaise() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateRaise(3);
    }

    @Test(expected = Exception.class)
    public void validateCheck() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        players.get(0).takeMoney(4);
        mv.validateCheck();
    }

}