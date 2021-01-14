package com.dburyak.exercise.game.bowling.service.io;

import java.io.InputStream;

/**
 * Implementation that reads data from STDIN.
 */
public class StdinGameHistoryInput implements GameHistoryInput {

    @Override
    public InputStream asInputStream() {
        return System.in;
    }
}
