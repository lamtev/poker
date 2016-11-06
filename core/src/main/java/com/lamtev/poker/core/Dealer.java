package com.lamtev.poker.core;

import java.util.List;

//TODO: специально Runtime Exception?
// это будет unchecked exception? здесь кажется уместнее checked
// можно до лучших времен бросать Exception, компилятор заставит декларировать их
// и обрабатывать
public class Dealer {

    private Cards cardDeck;
    private List<Player> players;
    private Cards commonCards;

    public Dealer(List<Player> players) {
        cardDeck = new CardDeck();
        this.players = players;
        commonCards = new Cards();
    }

    public Cards cardDeck() {
        return cardDeck;
    }

    public Cards commonCards() {
        return commonCards;
    }

    public void makePreflop() {
        if (preflopHasAlreadyBeen()) {
            //TODO normal exception
            throw new RuntimeException();
        }
        dealTwoCardsToPlayers();
    }

    public void makeFlop() {
        //TODO в этом конретном случае и подобных ниже по файлу легче было бы не с ! а с == false
        // ! не заметен на фоне || особенно в следующих подобных условиях
        //if (!preflopHasAlreadyBeen() || flopHasAlreadyBeen()) {
        if (isAbleToMakeFlop()) {
            for (int i = 0; i < 3; ++i) {
                commonCards.add(cardDeck.pickUpTop());
            }
        }
        else{
            //TODO normal exception
            throw new RuntimeException();
        }

    }

    public void makeTurn() {
        if (isAbleToMakeTurn()) {
            commonCards.add(cardDeck.pickUpTop());
        }
        else{
            //TODO normal exception
            throw new RuntimeException();
        }

    }



    public void makeRiver() {
        if (isAbleToMakeRiver()) {
            commonCards.add(cardDeck.pickUpTop());
        }
        else{
            //TODO normal exception
            throw new RuntimeException();
        }
    }

    private boolean preflopHasAlreadyBeen() {
        for (Player player : players) {
            if (player.cards().size() == 2) {
                //TODO: это ведь на первой же итерации для игрока с двумя картами вернет истину, так и надо?
                return true;
            }
        }

        return false;
    }

    //TODO: вот с этими методами как-то полегче
    private boolean isAbleToMakeFlop() {
        return preflopHasAlreadyBeen() && commonCards.size() == 0;
    }

    private boolean isAbleToMakeTurn() {
        return preflopHasAlreadyBeen() && commonCards.size() == 3;
    }

    private boolean isAbleToMakeRiver() {
        return preflopHasAlreadyBeen() && commonCards.size() == 4;
    }


    private boolean flopHasAlreadyBeen() {
        //TODO: если карт 2, значит флопа не было, по этой логике, но это тоже не корректный случай,
        // чтобы делать флоп должно быть ноль общих карт, получается неудачная логика
        return commonCards.size() >= 3;
    }

    private boolean turnHasAlreadyBeen() {
        return commonCards.size() >= 4;
    }

    private boolean riverHasAlreadyBeen() {
        return commonCards.size() == 5;
    }

    private void dealTwoCardsToPlayers() {
        for (int i = 0; i < 2; ++i) {
            players.forEach((x) -> x.takeCard(cardDeck.pickUpTop()));
        }
    }

}
