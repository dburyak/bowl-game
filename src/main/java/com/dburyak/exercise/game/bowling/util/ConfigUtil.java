package com.dburyak.exercise.game.bowling.util;

import com.dburyak.exercise.game.bowling.config.Config.InputSource;
import com.dburyak.exercise.game.bowling.config.Config.OutputDestination;

import java.net.MalformedURLException;
import java.net.URL;

import static com.dburyak.exercise.game.bowling.config.Config.InputSource.STDIN;
import static com.dburyak.exercise.game.bowling.config.Config.OutputDestination.STDOUT;

public class ConfigUtil {

    /**
     * Convert cli args value provided by user to application enum value.
     */
    public InputSource determineInputSource(String input) {
        if (input == null || input.isBlank()) {
            return null;
        } else if (input.equals("stdin")) {
            return STDIN;
        } else if (isUrl(input)) {
            return InputSource.URL;
        } else {
            return InputSource.FILE;
        }
    }

    /**
     * Convert cli args value provided by user to application enum value.
     */
    public OutputDestination determineOutputDestination(String output) {
        if (output == null || output.isBlank()) {
            return null;
        } else if (output.equals("stdout")) {
            return STDOUT;
        } else {
            return OutputDestination.FILE;
        }
    }

    public boolean isUrl(String str) {
        try {
            new URL(str);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
