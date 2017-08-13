package com.lamtev.poker.core.exceptions;

public class IsNotEnoughMoneyException extends Exception {

    public IsNotEnoughMoneyException(String additionalMessage) {
        super("There is not enough money to make a move. " + additionalMessage);
    }

}
