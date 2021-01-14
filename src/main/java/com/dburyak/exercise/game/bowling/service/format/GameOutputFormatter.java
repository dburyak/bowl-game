package com.dburyak.exercise.game.bowling.service.format;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Game;
import com.dburyak.exercise.game.bowling.service.io.GameOutput;

public interface GameOutputFormatter {
    void format(Game game, GameOutput output);

    static GameOutputFormatter create(Config config) {
        if (config.getOutputFormat() == Config.OutputFormat.TEXT_TAB_SEPARATED) {
            return new TenPinTabSeparatedGameOutputFormatter();
        } else {
            throw new IllegalArgumentException("output format is not supported: " + config.getOutputFormat());
        }
    }
}
