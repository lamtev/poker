package com.lamtev.poker.webserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Util {

    private static final List<String> NAMES = Arrays.asList(
            "Anna", "Arina", "Katya", "Vova", "Zhenya", "Zlatan", "Keanu",
            "Wayne", "Tereza", "George", "Max", "Vika", "Mel", "Donald");

    public static List<String> names(int number) {
        Collections.shuffle(NAMES);
        return new ArrayList<>(NAMES.subList(0, number));
    }

}
