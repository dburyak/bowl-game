package com.dburyak.exercise.game.bowling;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TenPinTabSeparatedInputTestHelper {

    public String buildInputAllStrikes(String player1, String player2) {
        return withStringWriter(out -> {
            IntStream.range(0, 12)
                    .forEach(rollNumber -> {
                        out.printf("%s\t%d\n", player1, 10);
                        out.printf("%s\t%d\n", player2, 10);
                    });
        });
    }

    public String buildInputAllFouls(String player1, String player2) {
        return withStringWriter(out -> {
            IntStream.range(0, 20)
                    .forEach(rollNumber -> {
                        out.printf("%s\tF\n", player1);
                        out.printf("%s\tF\n", player2);
                    });
        });
    }

    public String buildInputTooFewRolls(String player) {
        return withStringWriter(out -> {
            IntStream.range(0, 9)
                    .forEach(rollNumber -> {
                        out.printf("%s\t%d\n", player, rollNumber);
                    });
        });
    }

    public String buildInputTooManyRolls(String player) {
        return withStringWriter(out -> {
            IntStream.range(0, 30)
                    .forEach(rollNumber -> {
                        out.printf("%s\t%d\n", player, rollNumber % 11);
                    });
        });
    }

    public String buildInputForJeffVsJohnScenarioGame(String player1, String player2) {
        return withStringWriter(out -> {
            out.printf("%s\t%s\n", player1, 10);        // 1
            out.printf("%s\t%s\n", player2, 3);         // 2
            out.printf("%s\t%s\n", player2, 7);         // 3
            out.printf("%s\t%s\n", player1, 7);         // 4
            out.printf("%s\t%s\n", player1, 3);         // 5
            out.printf("%s\t%s\n", player2, 6);         // 6
            out.printf("%s\t%s\n", player2, 3);         // 7
            out.printf("%s\t%s\n", player1, 9);         // 8
            out.printf("%s\t%s\n", player1, 0);         // 9
            out.printf("%s\t%s\n", player2, 10);        // 10
            out.printf("%s\t%s\n", player1, 10);        // 11
            out.printf("%s\t%s\n", player2, 8);         // 12
            out.printf("%s\t%s\n", player2, 1);         // 13
            out.printf("%s\t%s\n", player1, 0);         // 14
            out.printf("%s\t%s\n", player1, 8);         // 15
            out.printf("%s\t%s\n", player2, 10);        // 16
            out.printf("%s\t%s\n", player1, 8);         // 17
            out.printf("%s\t%s\n", player1, 2);         // 18
            out.printf("%s\t%s\n", player2, 10);        // 19
            out.printf("%s\t%s\n", player1, "F");       // 20
            out.printf("%s\t%s\n", player1, 6);         // 21
            out.printf("%s\t%s\n", player2, 9);         // 22
            out.printf("%s\t%s\n", player2, 0);         // 23
            out.printf("%s\t%s\n", player1, 10);        // 24
            out.printf("%s\t%s\n", player2, 7);         // 25
            out.printf("%s\t%s\n", player2, 3);         // 26
            out.printf("%s\t%s\n", player1, 10);        // 27
            out.printf("%s\t%s\n", player2, 4);         // 28
            out.printf("%s\t%s\n", player2, 4);         // 29
            out.printf("%s\t%s\n", player1, 10);        // 30
            out.printf("%s\t%s\n", player1, 8);         // 31
            out.printf("%s\t%s\n", player1, 1);         // 32
            out.printf("%s\t%s\n", player2, 10);        // 33
            out.printf("%s\t%s\n", player2, 9);         // 34
            out.printf("%s\t%s\n", player2, 0);         // 35
        });
    }

    private String withStringWriter(Consumer<PrintWriter> action) {
        var writer = new StringWriter();
        try (var out = new PrintWriter(writer)) {
            action.accept(out);
        }
        return writer.toString();
    }
}
