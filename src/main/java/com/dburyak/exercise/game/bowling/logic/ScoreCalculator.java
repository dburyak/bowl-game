package com.dburyak.exercise.game.bowling.logic;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Match;

/**
 * Calculates match scores.
 */
public interface ScoreCalculator {
    void calculateScores(Match match);

    static ScoreCalculator create(Config config) {
        if (config.getRules() == Config.Rules.TEN_PIN) {
            return new TenPinScoreCalculator();
        } else {
            throw new IllegalArgumentException("unsupported game rules: " + config.getRules());
        }
    }
}
