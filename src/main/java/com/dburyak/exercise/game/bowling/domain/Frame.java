package com.dburyak.exercise.game.bowling.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Frame implements Iterable<Roll> {
    private Type type;
    private List<Roll> rolls;
    private int score;
    private int number;

    public enum Type {
        OPEN,
        SPARE,
        STRIKE;
    }

    @Override
    public Iterator<Roll> iterator() {
        return rolls.iterator();
    }

    public boolean hasSecondDelivery() {
        return rolls.size() > 1;
    }

    public boolean hasThirdDelivery() {
        return rolls.size() > 2;
    }

    public Roll getFirstRoll() {
        return rolls.get(0);
    }

    public Roll getSecondRoll() {
        return hasSecondDelivery() ? rolls.get(1) : null;
    }

    public Roll getThirdRoll() {
        return hasThirdDelivery() ? rolls.get(2) : null;
    }

    public boolean isOpenFrame() {
        return type == Type.OPEN;
    }

    public boolean isSpare() {
        return type == Type.SPARE;
    }

    public boolean isStrike() {
        return type == Type.STRIKE;
    }

    public int getTotalKnockedPins() {
        return rolls.stream()
                .mapToInt(Roll::getKnockedPins)
                .sum();
    }

    public boolean isLastFrame() {
        return number == 10;
    }

    public Frame addRoll(Roll roll) {
        rolls.add(roll);
        return this;
    }

    public int getRollsNumber() {
        return rolls.size();
    }
}
