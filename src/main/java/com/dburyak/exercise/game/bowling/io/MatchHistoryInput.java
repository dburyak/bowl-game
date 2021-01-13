package com.dburyak.exercise.game.bowling.io;

import java.io.InputStream;
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
     * Read data from source in text.
     *
     * @return reader
     */
    Reader asInputReader();

    /**
     * Read data from source in binary.
     *
     * @return input stream
     */
    InputStream asInputStream();
}
