package com.dburyak.exercise.game.bowling.config;

import lombok.Builder;
import lombok.Data;

/**
 * Application configuration.
 */
@Data
@Builder(toBuilder = true)
public class Config {
    private InputType inputType;
    private InputFormat inputFormat;
    private String input;
    private OutputType outputType;
    private OutputFormat outputFormat;
    private String output;
    private Rules rules;

    public enum InputType {
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

    public enum OutputType {
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

    public void mergeWith(Config... overrides) {
        // TODO: implement
    }
}
