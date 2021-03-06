package com.lamtev.poker.core.states;

import com.lamtev.poker.core.model.Bank;
import com.lamtev.poker.core.model.MoveValidator;
import com.lamtev.poker.core.model.Player;
import com.lamtev.poker.core.model.Players;
import com.lamtev.poker.core.exceptions.UnallowableMoveException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MoveValidatorTest {

    private Players generatePlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("a", 200));
        players.add(new Player("b", 200));
        players.add(new Player("c", 200));
        players.add(new Player("d", 200));
        players.add(new Player("e", 200));
        return new Players(players, "e");
    }

    @Test(expected = UnallowableMoveException.class)
    public void validateCall() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateCall();
    }

    @Test(expected = UnallowableMoveException.class)
    public void validateRaise() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateRaise(3);
    }

    @Test(expected = UnallowableMoveException.class)
    public void validateCheck() throws Exception {
        Players players = generatePlayers();
        Bank bank = new Bank(players);
        MoveValidator mv = new MoveValidator(players, bank);
        mv.validateCheck(1);
    }

}