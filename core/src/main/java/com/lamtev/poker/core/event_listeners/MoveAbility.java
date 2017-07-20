package com.lamtev.poker.core.event_listeners;

public class MoveAbility {

    private Boolean allInIsAble;
    private Boolean callIsAble;
    private Boolean checkIsAble;
    private Boolean foldIsAble;
    private Boolean raiseIsAble;
    private Boolean showdownIsAble;


    public MoveAbility() {
        this(false, false, false, false, false, false);
    }

    public MoveAbility(Boolean allInIsAble, Boolean callIsAble, Boolean checkIsAble,
                       Boolean foldIsAble, Boolean raiseIsAble, Boolean showdownIsAble) {
        this.allInIsAble = allInIsAble;
        this.callIsAble = callIsAble;
        this.checkIsAble = checkIsAble;
        this.foldIsAble = foldIsAble;
        this.raiseIsAble = raiseIsAble;
        this.showdownIsAble = showdownIsAble;
    }

    public Boolean allInIsAble() {
        return allInIsAble;
    }

    public void setAllInIsAble(Boolean allInIsAble) {
        this.allInIsAble = allInIsAble;
    }

    public Boolean callIsAble() {
        return callIsAble;
    }

    public void setCallIsAble(Boolean callIsAble) {
        this.callIsAble = callIsAble;
    }

    public Boolean checkIsAble() {
        return checkIsAble;
    }

    public void setCheckIsAble(Boolean checkIsAble) {
        this.checkIsAble = checkIsAble;
    }

    public Boolean foldIsAble() {
        return foldIsAble;
    }

    public void setFoldIsAble(Boolean foldIsAble) {
        this.foldIsAble = foldIsAble;
    }

    public Boolean raiseIsAble() {
        return raiseIsAble;
    }

    public void setRaiseIsAble(Boolean raiseIsAble) {
        this.raiseIsAble = raiseIsAble;
    }

    public Boolean showdownIsAble() {
        return showdownIsAble;
    }

    public void setShowdownIsAble(Boolean showdownIsAble) {
        this.showdownIsAble = showdownIsAble;
    }

}
