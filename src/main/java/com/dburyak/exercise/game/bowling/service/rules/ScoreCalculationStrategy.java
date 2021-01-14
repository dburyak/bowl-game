package com.dburyak.exercise.game.bowling.service.rules;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Game;

/**
 * Calculates game scores.
 */
public interface ScoreCalculationStrategy {
    void calculateScores(Game game);

    static ScoreCalculationStrategy create(Config config) {
        if (config.getRules() == Config.Rules.TEN_PIN) {
            return new TenPinScoreCalculationStrategy();
        } else {
            throw new IllegalArgumentException("unsupported game rules: " + config.getRules());
        }
    }
}
