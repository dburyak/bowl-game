package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.config.Config;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public interface ScoreOutput {

    OutputStream asOutputStream();

    default Writer asOutputWriter() {
        return new OutputStreamWriter(asOutputStream());
    }

    static ScoreOutput create(Config config) {
        if (config.getOutputDestination() == Config.OutputDestination.STDOUT) {
            return new StdoutScoreOutput();
        } else {
            throw new IllegalArgumentException("output destination is not supported: " + config.getOutputDestination());
        }
    }
}
