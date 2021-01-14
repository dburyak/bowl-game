package com.dburyak.exercise.game.bowling.service.io;

import java.io.OutputStream;

public class StdoutGameOutput implements GameOutput {
    @Override
    public OutputStream outputStream() {
        return System.out;
    }
}
