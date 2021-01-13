package com.dburyak.exercise.game.bowling.config;

/**
 * Config retrieving strategy (default hardcoded, cli args, env, sys props, external config file, etc).
 */
public interface ConfigRetriever {
    Config retrieve();
}
