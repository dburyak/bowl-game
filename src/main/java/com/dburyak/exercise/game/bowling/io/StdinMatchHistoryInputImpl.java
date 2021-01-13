package com.dburyak.exercise.game.bowling.io;

import java.io.InputStream;

/**
 * Implementation that reads data from STDIN.
 */
public class StdinMatchHistoryInputImpl implements MatchHistoryInput {

    @Override
    public InputStream asInputStream() {
        return System.in;
    }
}
