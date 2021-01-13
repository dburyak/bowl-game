package com.dburyak.exercise.game.bowling.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

/**
 * All the frames/deliveries performed by a single player during a match.
 * TODO: this class needs a better name, can't figure out any nice one at the moment
 */
@Data
@Builder(toBuilder = true)
public class PlayerPerformance implements Iterable<Frame> {
    private String playerName;
    private List<Frame> frames;
    private int totalScore;

    @Override
    public Iterator<Frame> iterator() {
        return frames.iterator();
    }
}
