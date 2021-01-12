package com.dburyak.exercise.game.bowling;

import java.util.concurrent.Callable;

public class CliApp implements Callable<Integer> {

    public static void main(String[] args) {
        System.out.println("I'm alive");
    }

    @Override
    public Integer call() throws Exception {
        return null;
    }
}
