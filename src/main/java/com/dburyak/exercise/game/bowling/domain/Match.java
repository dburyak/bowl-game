package com.dburyak.exercise.game.bowling.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Match implements Iterable<PlayerPerformance> {
    private List<PlayerPerformance> players;

    @Override
    public Iterator<PlayerPerformance> iterator() {
        return players.iterator();
    }

    public PlayerPerformance getPlayerPerformance(String player) {
        return players.stream()
                .filter(p -> p.getPlayerName().equals(player))
                .findFirst().orElseThrow();
    }
}
