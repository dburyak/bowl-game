package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Match;

public interface ScoreFormatter {
    void format(Match match, ScoreOutput output);

    static ScoreFormatter create(Config config) {
        if (config.getOutputFormat() == Config.OutputFormat.TEXT_TAB_SEPARATED) {
            return new TenPinTabSeparatedFormatter();
        } else {
            throw new IllegalArgumentException("output format is not supported: " + config.getOutputFormat());
        }
    }
}
