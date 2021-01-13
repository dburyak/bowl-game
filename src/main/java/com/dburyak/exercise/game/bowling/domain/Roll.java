package com.dburyak.exercise.game.bowling.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Roll {
    private int knockedPins;
    private Type type;

    public enum Type {
        HIT,
        MISS,
        GUTTER,
        FOUL
    }

    public boolean isHit() {
        return type == Type.HIT;
    }

    public boolean isMiss() {
        return type == Type.MISS;
    }

    public boolean isGutter() {
        return type == Type.GUTTER;
    }

    public boolean isFoul() {
        return type == Type.FOUL;
    }
}
