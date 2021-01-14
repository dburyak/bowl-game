package com.dburyak.exercise.game.bowling.service.format;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Game;
import com.dburyak.exercise.game.bowling.service.io.GameHistoryInput;

/**
 * Parser of game input.
 * We may have multiple implementations to support different input formats (csv, json, binary for example).
 */
public interface GameParser {
    Game parse(GameHistoryInput input);

    static GameParser create(Config config) {
        var isTabSeparated = config.getInputFormat() == Config.InputFormat.TEXT_TAB_SEPARATED;
        var isTenPin = config.getRules() == Config.Rules.TEN_PIN;
        if (isTabSeparated && isTenPin) {
            return  new TenPinTabSeparatedParser();
        } else {
            throw new IllegalArgumentException("input format not supported : "
                    + config.getInputFormat() + ", " + config.getRules());
        }
    }
}
