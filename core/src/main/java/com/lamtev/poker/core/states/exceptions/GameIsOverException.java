package com.lamtev.poker.core.states.exceptions;

// TODO: может быть вообще использовать стандартный IllegalStateException ? Или наследоваться от него?
public class GameIsOverException extends Exception {

    private static String MESSAGE = "Can't do action because game is over";

    public GameIsOverException() {
        super(MESSAGE);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
