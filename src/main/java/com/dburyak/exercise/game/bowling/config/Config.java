package com.dburyak.exercise.game.bowling.config;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Application configuration.
 */
@Data
@Builder(toBuilder = true)
public class Config {
    private InputSource inputSource;
    private InputFormat inputFormat;
    private String input;
    private OutputDestination outputDestination;
    private OutputFormat outputFormat;
    private String output;
    private Rules rules;

    public enum InputSource {
        STDIN,
        FILE,
        URL
    }

    public enum InputFormat {

        /**
         * Default format specified in requirements.
         */
        TEXT_TAB_SEPARATED
    }

    public enum OutputDestination {
        STDOUT,
        FILE
    }

    public enum OutputFormat {

        /**
         * Default format specified in requirements.
         */
        TEXT_TAB_SEPARATED
    }

    public enum Rules {
        TEN_PIN
    }

    public void mergeWith(Config otherConfig) {
        if (otherConfig.inputSource != null) {
            inputSource = otherConfig.inputSource;
        }
        if (otherConfig.inputFormat != null) {
            inputFormat = otherConfig.inputFormat;
        }
        if (otherConfig.input != null && !otherConfig.input.isBlank()) {
            input = otherConfig.input;
        }
        if (otherConfig.outputDestination != null) {
            outputDestination = otherConfig.outputDestination;
        }
        if (otherConfig.outputFormat != null) {
            outputFormat = otherConfig.outputFormat;
        }
        if (otherConfig.output != null && !otherConfig.output.isBlank()) {
            output = otherConfig.output;
        }
        if (otherConfig.rules != null) {
            rules = otherConfig.rules;
        }
    }

    public void mergeWith(Config... overrides) {
        for (Config otherCfg : overrides) {
            mergeWith(otherCfg);
        }
    }

    public static Config merge(Config baseConfig, Config... overrides) {
        baseConfig.mergeWith(overrides);
        return baseConfig;
    }

    public static Config merge(List<Config> configs) {
        if (configs.size() < 1) {
            throw new IllegalArgumentException("no configs to merge");
        }
        var baseConfig = configs.get(0);
        var overrides = new Config[configs.size() - 1];
        configs.subList(1, configs.size()).toArray(overrides);
        return merge(baseConfig, overrides);
    }
}
