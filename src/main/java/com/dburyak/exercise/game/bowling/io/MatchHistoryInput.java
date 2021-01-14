package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.config.Config;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Match input log reader.
 * Encapsulates all the details where to read input from and how to convert it to a chars or bytes stream.
 * <p>
 * Possible implementations:
 * <ul>
 *   <li>read input from stdin
 *   <li>read input from specific file
 *   <li>read input from url
 *  </ul>
 */
public interface MatchHistoryInput {

    /**
     * Read data from source as binary.
     *
     * @return input stream
     */
    InputStream asInputStream();

    /**
     * Read data from source as text.
     *
     * @return reader
     */
    default Reader asInputReader() {
        return new InputStreamReader(asInputStream());
    }

    static MatchHistoryInput create(Config config) {
        if (config.getInputSource() == Config.InputSource.STDIN) {
            return new StdinMatchHistoryInput();
        } else {
            throw new IllegalArgumentException("input source is not supported : " + config.getInputSource());
        }
    }
}
