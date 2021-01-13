package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.domain.Match;

/**
 * Parser of match input log.
 * We may have multiple implementations to support different input formats (csv, binary for example).
 */
public interface MatchHistoryParser {
    Match parse(MatchHistoryInput input);

    static MatchHistoryParser create(Config config) {
        var isTabSeparated = config.getInputFormat() == Config.InputFormat.TEXT_TAB_SEPARATED;
        var isTenPin = config.getRules() == Config.Rules.TEN_PIN;
        if (isTabSeparated && isTenPin) {
            return  new TenPinTabSeparatedParserImpl();
        } else {
            throw new IllegalArgumentException("input format not supported : "
                    + config.getInputFormat() + ", " + config.getRules());
        }
    }
}
