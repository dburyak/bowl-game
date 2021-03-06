package com.dburyak.exercise.game.bowling.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * All the frames/rolls performed by a single player during a game.
 * TODO: this class needs a better name, can't figure out any nice one at the moment
 */
@Data
@Builder(toBuilder = true)
public class PlayerPerformance implements Iterable<Frame> {
    private String playerName;
    private List<Frame> frames;

    @Override
    public Iterator<Frame> iterator() {
        return frames.iterator();
    }

    public List<Roll> getAllRolls() {
        return frames.stream()
                .flatMap(f -> f.getRolls().stream())
                .collect(Collectors.toList());
    }

    public PlayerPerformance addFrame(Frame frame) {
        frames.add(frame);
        return this;
    }

    public Frame getFirstFrame() {
        return frames.get(0);
    }

    public Frame getLastFrame() {
        return frames.get(frames.size() - 1);
    }
}
