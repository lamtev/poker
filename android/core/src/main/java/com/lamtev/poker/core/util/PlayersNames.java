package com.lamtev.poker.core.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class PlayersNames {

    private static final List<String> NAMES = Arrays.asList(
            "Anna", "Arina", "Katya", "Vova", "Alena", "Zhenya", "Olga", "Keanu",
            "Wayne", "Charlize", "Vadik", "Anastasia", "Max", "Vika", "Mel", "Donald",
            "Masha", "Alexandra", "Zlatan", "Michele", "Megan", "Nataly", "Yuriy",
            "Steve", "Bill", "Elon", "Alina", "Dimon", "Anton", "Artem", "Tanya",
            "Elizabeth", "Malorie", "George", "Rita", "Boris", "Alex", "Elena"
    );

    public static List<String> nNamesExceptThis(int n, String name) {
        Collections.shuffle(NAMES);
        List<String> names = new ArrayList<>();
        for (String it : NAMES) {
            if (!it.equals(name)) {
                names.add(it);
            }
        }
        return names.subList(0, n);
    }

}