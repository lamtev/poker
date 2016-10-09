package com.lamtev.poker.core;

import java.util.ArrayList;
import java.util.Random;

public class Dealer {

    private CardDeck cardDeck;
    private ArrayList<Player> players;
    private ArrayList<Card> commonCards;

    public Dealer(ArrayList<Player> players) {
        cardDeck = new CardDeck();
        this.players = players;
        commonCards = new ArrayList<>();
    }

    public CardDeck cardDeck() {
        return cardDeck;
    }

    public ArrayList<Player> players() {
        return players;
    }

    public ArrayList<Card> commonCards() {
        return commonCards;
    }

    public void shuffle() {
        Random rnd = new Random(13);
        for (int i = 0; i < 52; ++i) {
            int i1 = Math.abs(rnd.nextInt()*i - i*rnd.hashCode()) % 52;
            int i2 = Math.abs(rnd.nextInt()*13*i - i*i + 1234567) % 52;
            swap(cardDeck.cards, i1, i2);
        }
    }

    public void preflop() {
        for (int i = 0; i < 2; ++i) {
            for (Player player : players) {
                player.takeCard(cardDeck.giveTop());
            }
        }
    }

    public void flop() {
        if (!commonCards.isEmpty()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        for (int i = 0; i < 3; ++i) {
            putTopCardToCommonCards();
        }
    }

    public void turn() {
        if (commonCards.size() != 3) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    public void river() {
        if (commonCards.size() != 4) {
            //TODO normal exception
            throw new RuntimeException();
        }
        putTopCardToCommonCards();
    }

    private void putTopCardToCommonCards() {
        commonCards.add(cardDeck.giveTop());
    }

    private void swap(ArrayList<Card> cards, int index1, int index2) {
        Card card = cards.get(index1);
        cards.set(index1, cards.get(index2));
        cards.set(index2, card);
    }

}
