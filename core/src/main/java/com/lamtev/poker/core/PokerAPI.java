package com.lamtev.poker.core;

//TODO: не понятно, как играть через это апи
// методы ничего не принимают, ничего не возвращают
// вот дали мне это апи, делаю я клиента для покера,
// опрашиваю пользователей "чего изволите" для простоты последовательно (последовательно ходят?).
// хочу там игроков создать, ставки, карты брать/не брать, отрисовать у кого какие. Узнать кто победил...
// А как??

import java.util.List;

public interface PokerAPI {
    void start(List<Object> listWithPlayersInfo, int smallBlindSize);
    int getPlayerWager(int playerID);
    int getPlayerStack(int playerID);
    int getBank();
    Cards getPlayerCards(int playerID);
    Cards getCommonCards();
    void call() throws Exception;
    void raise(int additionalWager) throws Exception;
    void fold() throws Exception;
    void check() throws Exception;
}
