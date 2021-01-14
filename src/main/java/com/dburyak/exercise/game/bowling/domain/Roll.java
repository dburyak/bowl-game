package com.dburyak.exercise.game.bowling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
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

    public static Roll foul() {
        return new Roll(0, Type.FOUL);
    }

    public static Roll strike() {
        return new Roll(10, Type.HIT);
    }

    public static Roll miss() {
        return new Roll(0, Type.MISS);
    }

    public static Roll hit(int knockedPins) {
        return new Roll(knockedPins, Type.HIT);
    }
}
