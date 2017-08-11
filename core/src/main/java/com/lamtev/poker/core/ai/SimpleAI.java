package com.lamtev.poker.core.ai;

public class SimpleAI extends AbstractAI {

    public SimpleAI(String id, int stack) {
        super(id, stack);
    }

    @Override
    public void makeAMove() {
        if (!id().equals(currentPlayer())) {
            throw new RuntimeException("Can not make a move in case i am " + id() + " but current is " + currentPlayer());
        }
        try {
            switch (state()) {
                case "PreflopWageringState":
                case "FlopWageringState":
                case "TurnWageringState":
                case "RiverWageringState":
                    if (moveAbility.checkIsAble()) {
                        poker().check();
                    } else if (moveAbility.callIsAble()) {
                        poker().call();
                    } else if (moveAbility.allInIsAble()) {
                        poker().allIn();
                    } else {
                        poker().fold();
                    }
                    break;
                case "ShowdownState":
                    poker().showDown();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SimpleAI{" +
                "id='" + id() + '\'' +
                ", stack=" + stack() +
                ", wager=" + wager() +
                ", cards=" + cards() +
                ", isActive=" + isActive() +
                '}';
    }

}
