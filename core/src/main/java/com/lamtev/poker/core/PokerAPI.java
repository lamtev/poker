package com.lamtev.poker.core;

//TODO: не понятно, как играть через это апи
// методы ничего не принимают, ничего не возвращают
// вот дали мне это апи, делаю я клиента для покера,
// опрашиваю пользователей "чего изволите" для простоты последовательно (последовательно ходят?).
// хочу там игроков создать, ставки, карты брать/не брать, отрисовать у кого какие. Узнать кто победил...
// А как??

import java.util.LinkedHashMap;
import java.util.Map;

public interface PokerAPI {
    //TODO start()
    void start(LinkedHashMap<String, Integer> playersInfo, int smallBlindSize, String smallBlindID);
    int getPlayerWager(String playerID);
    int getPlayerStack(String playerID);
    int getMoneyInBank();
    Cards getPlayerCards(String playerID);
    Cards getCommonCards();
    LinkedHashMap<String, Integer> getPlayersInfo();
    void call() throws Exception;
    void raise(int additionalWager) throws Exception;
    void fold() throws Exception;
    void check() throws Exception;
}
