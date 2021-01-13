package com.dburyak.exercise.game.bowling;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command()
public class BowlingCliApp implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 0;
    }

    public static void main(String[] args) {
        var exitCode = new CommandLine(new BowlingCliApp()).execute(args);
        System.exit(exitCode);
    }
}
