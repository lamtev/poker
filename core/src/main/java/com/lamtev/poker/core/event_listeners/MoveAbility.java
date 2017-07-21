package com.lamtev.poker.core.event_listeners;

public class MoveAbility {

    private boolean allInIsAble;
    private boolean callIsAble;
    private boolean checkIsAble;
    private boolean foldIsAble;
    private boolean raiseIsAble;
    private boolean showdownIsAble;


    public MoveAbility() {

    }

    public MoveAbility(boolean allInIsAble, boolean callIsAble, boolean checkIsAble,
                       boolean foldIsAble, boolean raiseIsAble, boolean showdownIsAble) {
        this.allInIsAble = allInIsAble;
        this.callIsAble = callIsAble;
        this.checkIsAble = checkIsAble;
        this.foldIsAble = foldIsAble;
        this.raiseIsAble = raiseIsAble;
        this.showdownIsAble = showdownIsAble;
    }

    public boolean allInIsAble() {
        return allInIsAble;
    }

    public void setAllInIsAble(boolean allInIsAble) {
        this.allInIsAble = allInIsAble;
    }

    public boolean callIsAble() {
        return callIsAble;
    }

    public void setCallIsAble(boolean callIsAble) {
        this.callIsAble = callIsAble;
    }

    public boolean checkIsAble() {
        return checkIsAble;
    }

    public void setCheckIsAble(boolean checkIsAble) {
        this.checkIsAble = checkIsAble;
    }

    public boolean foldIsAble() {
        return foldIsAble;
    }

    public void setFoldIsAble(boolean foldIsAble) {
        this.foldIsAble = foldIsAble;
    }

    public boolean raiseIsAble() {
        return raiseIsAble;
    }

    public void setRaiseIsAble(boolean raiseIsAble) {
        this.raiseIsAble = raiseIsAble;
    }

    public boolean showdownIsAble() {
        return showdownIsAble;
    }

    public void setShowdownIsAble(boolean showdownIsAble) {
        this.showdownIsAble = showdownIsAble;
    }

}
