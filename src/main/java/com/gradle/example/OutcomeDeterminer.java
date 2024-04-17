package com.gradle.example;

import java.util.Map;
import java.util.Set;

public class OutcomeDeterminer {
    public static String getOutcome(String userMove, String computerMove, Map<String, Set<String>> movesItCanDefeat) {
        if (userMove.equals(computerMove)) {
            return "Draw";
        } else if (movesItCanDefeat.get(userMove).contains(computerMove)) {
            return "Win";
        } else {
            return "Lose";
        }
    }
}
