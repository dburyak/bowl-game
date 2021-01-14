package com.dburyak.exercise.game.bowling.io;

import java.io.OutputStream;

public class StdoutScoreOutput implements ScoreOutput {
    @Override
    public OutputStream asOutputStream() {
        return System.out;
    }
}
