package com.lamtev.poker.core.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class PlayersNames {

    private static final List<String> NAMES = Arrays.asList(
            "Anna", "Arina", "Katya", "Vova", "Alena", "Zhenya", "Olga", "Keanu",
            "Wayne", "Charlize", "Anastasia", "Max", "Vika", "Mel", "Donald",
            "Masha", "Alexandra", "Zlatan", "Michele", "Megan", "Nataly",
            "Steve", "Bill", "Elon", "Alina", "Dimon", "Anton", "Artem", "Tanya",
            "Elizabeth", "Malorie", "George", "Rita", "Boris", "Alex", "Elena"
    );

    public static List<String> nNamesExceptThis(int n, String name) {
        Collections.shuffle(NAMES);
        return NAMES.stream()
                .filter(it -> !it.equals(name))
                .collect(Collectors.toList())
                .subList(0, n);
    }

}