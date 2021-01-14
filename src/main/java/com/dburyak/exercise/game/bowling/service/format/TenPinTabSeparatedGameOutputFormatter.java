package com.dburyak.exercise.game.bowling.service.format;

import com.dburyak.exercise.game.bowling.domain.Frame;
import com.dburyak.exercise.game.bowling.domain.Game;
import com.dburyak.exercise.game.bowling.service.io.GameOutput;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class TenPinTabSeparatedGameOutputFormatter implements GameOutputFormatter {
    private static final String SYMB_STRIKE = "X";
    private static final String SYMB_SPARE = "/";
    private static final String SYMB_FOUL = "F";

    @Override
    public void format(Game game, GameOutput output) {
        try (var outWriter = new PrintWriter(new BufferedWriter(output.outputWriter()))) {
            outWriter.print("Frame");
            for (int i = 1; i <= 10; i++) {
                outWriter.printf("\t\t%d", i);
            }
            outWriter.println();
            for (var performance : game) {
                outWriter.println(performance.getPlayerName());
                outWriter.print("Pinfalls");
                for (var frame : performance) {
                    printRolls(frame, outWriter);
                }
                outWriter.println();
                outWriter.print("Score");
                for (var frame : performance) {
                    outWriter.printf("\t\t%d", frame.getScore());
                }
                outWriter.println();
            }
        }
    }

    private void printRolls(Frame frame, PrintWriter writer) {
        var isLastFrame = (frame.getNumber() == 10);
        if (frame.isStrike()) {
            if (!isLastFrame) {
                if (!frame.getFirstRoll().isFoul()) {
                    writer.print("\t\tX");
                } else {
                    writer.print("\tF\tX");
                }
            } else { // last frame strike
                var roll2Pins = frame.getSecondRoll().getKnockedPins();
                var roll3Pins = frame.getThirdRoll().getKnockedPins();
                var roll2Sym = (roll2Pins == 10) ? SYMB_STRIKE
                        : frame.getSecondRoll().isFoul() ? SYMB_FOUL
                        : roll2Pins;
                var roll3Sym = (roll3Pins == 10) ? SYMB_STRIKE
                        : (roll2Pins + roll3Pins == 10) ? SYMB_SPARE
                        : roll3Pins;
                writer.printf("\tX\t%s\t%s", roll2Sym, roll3Sym);
            }
        } else if (frame.isSpare()) {
            var roll1Pins = frame.getFirstRoll().getKnockedPins();
            if (!isLastFrame) {
                writer.printf("\t%d\t%s", roll1Pins, SYMB_SPARE);
            } else { // last frame spare
                var roll3Pins = frame.getThirdRoll().getKnockedPins();
                var roll3Sym = (roll3Pins == 10) ? SYMB_STRIKE
                        : frame.getThirdRoll().isFoul() ? SYMB_FOUL
                        : roll3Pins;
                writer.printf("\t%d\t%s\t%s", roll1Pins, SYMB_SPARE, roll3Sym);
            }
        } else { // open frame - always 2 rolls
            var roll1Pins = frame.getFirstRoll().getKnockedPins();
            var roll2Pins = frame.getSecondRoll().getKnockedPins();
            var roll1Sym = frame.getFirstRoll().isFoul() ? SYMB_FOUL : roll1Pins;
            var roll2Sym = frame.getSecondRoll().isFoul() ? SYMB_FOUL : roll2Pins;
            writer.printf("\t%s\t%s", roll1Sym, roll2Sym);
        }
    }
}
