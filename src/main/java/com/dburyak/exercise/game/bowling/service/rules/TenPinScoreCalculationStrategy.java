package com.dburyak.exercise.game.bowling.service.rules;

import com.dburyak.exercise.game.bowling.domain.Game;

public class TenPinScoreCalculationStrategy implements ScoreCalculationStrategy {

    @Override
    public void calculateScores(Game game) {
        for (var performance : game) {
            var allPlayerRolls = performance.getAllRolls();
            var rollNum = 0;
            var previousFrameScore = 0;
            for (var frame : performance) {
                var firstRoll = allPlayerRolls.get(rollNum);
                var secondRoll = allPlayerRolls.get(rollNum + 1);
                if (firstRoll.isFoul() && frame.isStrike()) {
                    // foul+strike frame, we need to look at 4 rolls at once
                    var thirdRoll = allPlayerRolls.get(rollNum + 2);
                    var fourthRoll = allPlayerRolls.get(rollNum + 3);
                    var nextTwoRollsPins = thirdRoll.getKnockedPins() + fourthRoll.getKnockedPins();
                    frame.setScore(previousFrameScore + secondRoll.getKnockedPins() + nextTwoRollsPins);
                    rollNum += 2;
                } else if (frame.isStrike() || frame.isSpare()) {
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
