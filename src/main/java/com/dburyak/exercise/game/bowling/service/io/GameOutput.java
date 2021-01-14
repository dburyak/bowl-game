package com.dburyak.exercise.game.bowling.service.io;

import com.dburyak.exercise.game.bowling.config.Config;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public interface GameOutput {

    OutputStream outputStream();

    default Writer outputWriter() {
        return new OutputStreamWriter(outputStream());
    }

    static GameOutput create(Config config) {
        if (config.getOutputDestination() == Config.OutputDestination.STDOUT) {
            return new StdoutGameOutput();
        } else if (config.getOutputDestination() == Config.OutputDestination.FILE) {
            return new FileGameOutput(new File(config.getOutput()));
        } else {
            throw new IllegalArgumentException("output destination is not supported: " + config.getOutputDestination());
        }
    }
}
