package com.dburyak.exercise.game.bowling.logic;

import com.dburyak.exercise.game.bowling.domain.Match;

public class TenPinScoreCalculator implements ScoreCalculator {

    @Override
    public void calculateScores(Match match) {
        for (var performance : match) {
            var allPlayerRolls = performance.getAllRolls();
            var rollNum = 0;
            var previousFrameScore = 0;
            for (var frame : performance) {
                var firstRoll = allPlayerRolls.get(rollNum);
                var secondRoll = allPlayerRolls.get(rollNum + 1);
                if (frame.isStrike() || frame.isSpare()) {
                    var thirdRoll = allPlayerRolls.get(rollNum + 2);
                    var nextTwoRollsPins = secondRoll.getKnockedPins() + thirdRoll.getKnockedPins();
                    frame.setScore(previousFrameScore + firstRoll.getKnockedPins() + nextTwoRollsPins);
                    rollNum += frame.isStrike() ? 1 : 2;
                } else { // OPEN frame
                    frame.setScore(previousFrameScore + firstRoll.getKnockedPins() + secondRoll.getKnockedPins());
                    rollNum += 2;
                }
                previousFrameScore = frame.getScore();
            }
        }
    }
}
