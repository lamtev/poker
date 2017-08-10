package com.lamtev.poker.core.ai;

import com.lamtev.poker.core.hands.PokerHand;
import com.lamtev.poker.core.hands.PokerHandFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ThinkingAI extends AbstractAI {

    public ThinkingAI(String id, int stack) {
        super(id, stack);
    }

    @Override
    public void makeAMove() {
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
                    PokerHandFactory handFactory = new PokerHandFactory(communityCards);
                    PokerHand itsHand = handFactory.createCombination(cards());
                    Optional<PokerHand> betterHand = handsOfPotConcurrents().stream()
                            .filter(it -> it.compareTo(itsHand) > 0)
                            .findFirst();
                    if (betterHand.isPresent() && moveAbility.foldIsAble()) {
                        poker().fold();
                    } else {
                        poker().showDown();
                    }
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
        return "ThinkingAI{" +
                "id='" + id() + '\'' +
                ", stack=" + stack() +
                ", wager=" + wager() +
                ", cards=" + cards() +
                ", isActive=" + isActive() +
                '}';
    }

    private List<PokerHand> handsOfPotConcurrents() {
        List<PokerHand> handsOfPotConcurrents = new ArrayList<>();
        playersHands.forEach((id, hand) -> {
            if (!id.equals(id()) && contenders.get(id).wager() <= wager()) {
                handsOfPotConcurrents.add(hand);
            }
        });
        return handsOfPotConcurrents;
    }

}
