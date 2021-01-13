package com.dburyak.exercise.game.bowling.config;

import com.dburyak.exercise.game.bowling.config.Config.InputFormat;
import com.dburyak.exercise.game.bowling.config.Config.OutputFormat;

import static com.dburyak.exercise.game.bowling.config.Config.InputSource.STDIN;
import static com.dburyak.exercise.game.bowling.config.Config.OutputDestination.STDOUT;
import static com.dburyak.exercise.game.bowling.config.Config.Rules.TEN_PIN;

/**
 * Default hardcoded config.
 */
public class DefaultConfigRetriever implements ConfigRetriever {

    @Override
    public Config retrieve() {
        return Config.builder()
                .inputSource(STDIN)
                .inputFormat(InputFormat.TEXT_TAB_SEPARATED)
                .input("stdin")
                .outputDestination(STDOUT)
                .outputFormat(OutputFormat.TEXT_TAB_SEPARATED)
                .output("stdout")
                .rules(TEN_PIN)
                .build();
    }
}
