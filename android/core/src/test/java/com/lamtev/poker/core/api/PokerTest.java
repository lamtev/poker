package com.lamtev.poker.core.api;

import com.lamtev.poker.core.TestUtils.Action;
import com.lamtev.poker.core.exceptions.UnallowableMoveException;
import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.model.Card;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.lamtev.poker.core.TestUtils.nTimes;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PokerTest implements Play {

    private String state;
    private List<User> users;
    private List<Player> players = new ArrayList<>();
    private List<Card> communityCards;
    private Map<String, PokerHand> hands;
    private String currentPlayerId;
    private int bank;

    private List<User> generatePlayers() {
        List<User> playersInfo = new ArrayList<>();
        playersInfo.add(new User("b", 500));
        playersInfo.add(new User("c", 500));
        playersInfo.add(new User("d", 500));
        playersInfo.add(new User("e", 500));
        playersInfo.add(new User("f", 500));
        playersInfo.add(new User("a", 500));
        return playersInfo;
    }

    @Before
    public void cleanUp() {
        state = null;
        communityCards = new ArrayList<>();
        hands = new HashMap<>();
        currentPlayerId = null;
        users = generatePlayers();
        players.clear();
        players.addAll(users);
        bank = 0;
    }

    @Test
    public void test() throws Exception {

        final RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId("a")
                .setSmallBlindWager(5)
                .registerPlay(this)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals(15, bank);
        for (User user : users) {
            assertEquals(2, user.cards().size());
        }
        assertEquals("d", currentPlayerId);

        poker.call();
        assertEquals("e", currentPlayerId);

        poker.raise(90);
        assertEquals("f", currentPlayerId);

        nTimes(5, new Action() {
            @Override
            public void run() throws Exception {
                poker.call();
            }
        });
        assertEquals("FlopWageringState", state);
        assertEquals(600, bank);
        assertEquals(3, communityCards.size());
        assertEquals("b", currentPlayerId);

        nTimes(6, new Action() {
            @Override
            public void run() throws Exception {
                poker.check();
            }
        });
        assertEquals("TurnWageringState", state);
        assertEquals(4, communityCards.size());
        assertEquals("b", currentPlayerId);

        nTimes(6, new Action() {
            @Override
            public void run() throws Exception {
                poker.check();
            }
        });
        assertEquals("RiverWageringState", state);
        assertEquals(5, communityCards.size());
        assertEquals("b", currentPlayerId);

        poker.raise(100);
        poker.raise(100);
        poker.raise(100);
        nTimes(5, new Action() {
            @Override
            public void run() throws Exception {
                poker.call();
            }
        });
        assertEquals("ShowdownState", state);
        assertEquals(2400, bank);
        for (User user : users) {
            assertEquals(100, user.stack());
            assertEquals(400, user.wager());
        }
        assertEquals("d", currentPlayerId);

        for (Player player : players) {
            assertTrue(hands.get(player.id()) == null);
        }

        for (Player player : players) {
            try {
                poker.showDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Player player : players) {
            if (hands.containsKey(player.id())) {
                System.out.println(player.id() + ": " + hands.get(player.id()));
            }
        }

        assertEquals("RoundOfPlayIsOverState", state);

        int actualPlayersStackSum = 0;
        for (Player player : players) {
            actualPlayersStackSum += player.stack();
        }
        assertEquals(3000, actualPlayersStackSum);
        for (Player it : players) {
            System.out.println(it.id() + ": " + it.stack());
        }
    }

    @Test
    public void testAllIn() throws Exception {

        final RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId("a")
                .setSmallBlindWager(5)
                .registerPlay(this)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals(15, bank);
        for (User user : users) {
            assertEquals(2, user.cards().size());
        }

        poker.allIn();
        nTimes(5, new Action() {
            @Override
            public void run() throws Exception {
                poker.call();
            }
        });

        assertEquals("ShowdownState", state);
        assertEquals(3000, bank);
        assertEquals(5, communityCards.size());

        for (Player player : players) {
            assertTrue(hands.get(player.id()) == null);
        }

        for (Player player : players) {
            try {
                poker.showDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Player player : players) {
            if (hands.containsKey(player.id())) {
                System.out.println(player.id() + ": " + hands.get(player.id()));
            }
        }

        assertEquals("RoundOfPlayIsOverState", state);

        int actualPlayersStackSum = 0;
        for (Player player : players) {
            actualPlayersStackSum += player.stack();
        }
        assertEquals(3000, actualPlayersStackSum);
        for (Player it : players) {
            System.out.println(it.id() + ": " + it.stack());
        }
    }

    @Test
    public void testAllIn2Players() throws Exception {
        List<Player> players = asList((Player) new User("a", 700),  new User("b", 500));
        RoundOfPlay poker = new PokerBuilder()
                .setSmallBlindWager(150)
                .registerPlayers(players)
                .setDealerId("a")
                .registerPlay(this)
                .create();

        poker.call();
        poker.allIn();
        poker.allIn();
        assertEquals("ShowdownState", state);
    }

    @Test(expected = UnallowableMoveException.class)
    public void testRaise() throws Exception {
        ((User) players.get(1)).setStack(600);

        final RoundOfPlay poker = new PokerBuilder()
                .registerPlayers(players)
                .setDealerId("a")
                .setSmallBlindWager(5)
                .registerPlay(this)
                .create();

        assertEquals("PreflopWageringState", state);
        assertEquals(15, bank);
        for (User it : users) {
            assertEquals(2, it.cards().size());
        }

        poker.allIn();
        nTimes(4, new Action() {
            @Override
            public void run() throws Exception {
                poker.call();
            }
        });
        poker.raise(30);
    }

    @Override
    public void stateChanged(String stateName) {
        this.state = stateName;
    }

    @Override
    public void playerFold(String playerId) {
        for (User it : users) {
            if (it.id().equals(playerId)) {
                it.setActive(false);
                return;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void playerMoneyUpdated(String playerId, int playerStack, int playerWager) {
        for (User it : users) {
            if (it.id().equals(playerId)) {
                it.setStack(playerStack);
                it.setWager(playerWager);
                return;
            }
        }
        throw new RuntimeException();
    }

    @Override
    public void currentPlayerChanged(String currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    @Override
    public void holeCardsDealt(Map<String, List<Card>> playerIdToCards) {
        Set<Map.Entry<String, List<Card>>> entrySet = playerIdToCards.entrySet();
        for (Map.Entry<String, List<Card>> entry : entrySet) {
            String id = entry.getKey();
            List<Card> cards = entry.getValue();
            for (User user : users) {
                if (user.id().equals(id)) {
                    user.setCards(cards);
                    break;
                }
            }

        }
    }

    @Override
    public void communityCardsDealt(List<Card> addedCommunityCards) {
        communityCards.addAll(addedCommunityCards);
    }

    @Override
    public void playerShowedDown(String playerId, PokerHand hand) {
        hands.put(playerId, hand);
    }

    @Override
    public void bankMoneyUpdated(int money, int wager) {
        bank = money;
    }

    @Override
    public void blindWagersPlaced() {

    }

    @Override
    public void playerAllinned(String playerId) {

    }

    @Override
    public void playerCalled(String playerId) {

    }

    @Override
    public void playerChecked(String playerId) {

    }

    @Override
    public void playerRaised(String playerId) {

    }

    @Override
    public void callAbilityChanged(boolean flag) {

    }

    @Override
    public void raiseAbilityChanged(boolean flag) {

    }

    @Override
    public void allInAbilityChanged(boolean flag) {

    }

    @Override
    public void checkAbilityChanged(boolean flag) {

    }

    @Override
    public void foldAbilityChanged(boolean flag) {

    }

    @Override
    public void showDownAbilityChanged(boolean flag) {

    }

    @Override
    @Deprecated
    final public String id() {
        throw new RuntimeException();
    }

    @Override
    @Deprecated
    final public void holeCardsDealt(List<Card> holeCards) {
        throw new RuntimeException();
    }

    @Override
    @Deprecated
    final public void playerShowedDown(String playerId, List<Card> holeCards, PokerHand hand) {
        throw new RuntimeException();
    }

}
