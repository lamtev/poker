package com.lamtev.poker.core;

public class TestUtils {

    public interface Action {
        void run() throws Exception;
    }

    public static void nTimes(int n, Action action) throws Exception {
        for (int i = 0; i < n; ++i) action.run();
    }

}
