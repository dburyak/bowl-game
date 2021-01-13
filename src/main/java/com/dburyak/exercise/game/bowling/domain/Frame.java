package com.dburyak.exercise.game.bowling.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Frame implements Iterable<Delivery> {
    private Type type;
    private List<Delivery> deliveries;
    private int score;

    public enum Type {
        OPEN,
        SPARE,
        STRIKE;
    }

    @Override
    public Iterator<Delivery> iterator() {
        return deliveries.iterator();
    }

    public boolean hasSecondDelivery() {
        return deliveries.size() > 1;
    }

    public boolean hasThirdDelivery() {
        return deliveries.size() > 2;
    }

    public Delivery getFirstDelivery() {
        return deliveries.get(0);
    }

    public Delivery getSecondDelivery() {
        return hasSecondDelivery() ? deliveries.get(1) : null;
    }

    public Delivery getThirdDelivery() {
        return hasThirdDelivery() ? deliveries.get(2) : null;
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
}
