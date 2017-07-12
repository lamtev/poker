package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;
import org.junit.Test;

public class MoveValidatorTest {

    private Players generatePlayers() {
        Players players = new Players();
        players.add(new Player("a", 200));
        players.add(new Player("b", 200));
        players.add(new Player("c", 200));
        players.add(new Player("d", 200));
        players.add(new Player("e", 200));
        players.setDealer("e");
        return players;
    }

    @Test(expected = Exception.class)
    public void validateCall() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateCall(players.get(0));
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
        mv.validateCheck(1);
    }

}