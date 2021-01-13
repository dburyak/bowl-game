package com.dburyak.exercise.game.bowling.config;

import com.dburyak.exercise.game.bowling.BowlingCliApp;
import com.dburyak.exercise.game.bowling.util.ConfigUtil;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CliArgsConfigRetriever implements ConfigRetriever {
    private BowlingCliApp app;
    private ConfigUtil configUtil;

    @Override
    public Config retrieve() {
        return Config.builder()
                .inputSource(configUtil.determineInputSource(app.getInput()))
                .inputFormat(toInputFormat(app.getInputFormat()))
                .input(app.getInput())
                .outputDestination(configUtil.determineOutputDestination(app.getOutput()))
                .outputFormat(toOutputFormat(app.getOutputFormat()))
                .output(app.getOutput())
                .rules(toRules(app.getRules()))
                .build();
    }

    private Config.InputFormat toInputFormat(BowlingCliApp.InputFormat cliInputFormat) {
        if (cliInputFormat == null) {
            return null;
        } else if (cliInputFormat == BowlingCliApp.InputFormat.textTabSeparated) {
            return Config.InputFormat.TEXT_TAB_SEPARATED;
        } else {
            throw new IllegalArgumentException("cliInputFormat of unknown type : " + cliInputFormat);
        }
    }

    private Config.OutputFormat toOutputFormat(BowlingCliApp.OutputFormat cliOutputFormat) {
        if (cliOutputFormat == null) {
            return null;
        } else if (cliOutputFormat == BowlingCliApp.OutputFormat.textTabSeparated) {
            return Config.OutputFormat.TEXT_TAB_SEPARATED;
        } else {
            throw new IllegalArgumentException("cliOutputFormat of unknown type : " + cliOutputFormat);
        }
    }

    private Config.Rules toRules(BowlingCliApp.Rules cliRules) {
        if (cliRules == null) {
            return null;
        } else if (cliRules == BowlingCliApp.Rules.tenPin) {
            return Config.Rules.TEN_PIN;
        } else {
            throw new IllegalArgumentException("cliRules of unknown type : " + cliRules);
        }
    }
}
