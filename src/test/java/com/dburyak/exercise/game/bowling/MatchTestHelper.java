package com.dburyak.exercise.game.bowling;

import com.dburyak.exercise.game.bowling.domain.Frame;
import com.dburyak.exercise.game.bowling.domain.Match;
import com.dburyak.exercise.game.bowling.domain.PlayerPerformance;
import com.dburyak.exercise.game.bowling.domain.Roll;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MatchTestHelper {
    public Match buildMatchAllStrikes(String player1, String player2) {
        return Match.builder()
                .players(Stream.of(player1, player2)
                        .map(this::buildPerformanceAllStrikes)
                        .collect(Collectors.toList()))
                .build();
    }

    public Match buildMatchAllStrikesNotScored(String player1, String player2) {
        return Match.builder()
                .players(Stream.of(player1, player2)
                        .map(this::buildPerformanceAllStrikesNotScored)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public PlayerPerformance buildPerformanceAllStrikes(String player) {
        var performance = PlayerPerformance.builder()
                .totalScore(300)
                .playerName(player)
                .frames(new ArrayList<>())
                .build();
        for (int rollNum = 1; rollNum < 10; rollNum++) {
            var frame = Frame.builder()
                    .number(rollNum)
                    .type(Frame.Type.STRIKE)
                    .score(rollNum * 30)
                    .rolls(buildHits(1, 10))
                    .build();
            performance.addFrame(frame);
        }
        performance.addFrame(Frame.builder() // final frame
                .number(10)
                .type(Frame.Type.STRIKE)
                .score(300)
                .rolls(buildHits(3, 10))
                .build());
        return performance;
    }

    public PlayerPerformance buildPerformanceAllStrikesNotScored(String player) {
        var performance = PlayerPerformance.builder()
                .totalScore(0)
                .playerName(player)
                .frames(new ArrayList<>())
                .build();
        for (int rollNum = 1; rollNum < 10; rollNum++) {
            var frame = Frame.builder()
                    .number(rollNum)
                    .type(Frame.Type.STRIKE)
                    .score(0)
                    .rolls(buildHits(1, 10))
                    .build();
            performance.addFrame(frame);
        }
        performance.addFrame(Frame.builder() // final frame
                .number(10)
                .type(Frame.Type.STRIKE)
                .score(0)
                .rolls(buildHits(3, 10))
                .build());
        return performance;
    }

    public Match buildMatchAllFouls(String player1, String player2) {
        return Match.builder()
                .players(Stream.of(player1, player2)
                        .map(this::buildPerformanceAllFouls)
                        .collect(Collectors.toList()))
                .build();
    }

    public PlayerPerformance buildPerformanceAllFouls(String player) {
        var performance = PlayerPerformance.builder()
                .totalScore(0)
                .playerName(player)
                .frames(new ArrayList<>())
                .build();
        for (int rollNum = 1; rollNum <= 10; rollNum++) {
            performance.addFrame(Frame.builder()
                    .number(rollNum)
                    .type(Frame.Type.OPEN)
                    .score(0)
                    .rolls(buildFouls(2))
                    .build());
        }
        return performance;
    }

    public Match buildMatchSample1(String player1, String player2) {
        var performanceJeff = PlayerPerformance.builder()
                .playerName(player1)
                .totalScore(167)
                .frames(List.of(
                        Frame.builder()
                                .number(1)
                                .type(Frame.Type.STRIKE)
                                .score(20)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(2)
                                .type(Frame.Type.SPARE)
                                .score(39)
                                .rolls(List.of(Roll.hit(7), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(3)
                                .type(Frame.Type.OPEN)
                                .score(48)
                                .rolls(List.of(Roll.hit(9), Roll.miss()))
                                .build(),
                        Frame.builder()
                                .number(4)
                                .type(Frame.Type.STRIKE)
                                .score(66)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(5)
                                .type(Frame.Type.OPEN)
                                .score(74)
                                .rolls(List.of(Roll.miss(), Roll.hit(8)))
                                .build(),
                        Frame.builder()
                                .number(6)
                                .type(Frame.Type.SPARE)
                                .score(84)
                                .rolls(List.of(Roll.hit(8), Roll.hit(2)))
                                .build(),
                        Frame.builder()
                                .number(7)
                                .type(Frame.Type.OPEN)
                                .score(90)
                                .rolls(List.of(Roll.foul(), Roll.hit(6)))
                                .build(),
                        Frame.builder()
                                .number(8)
                                .type(Frame.Type.STRIKE)
                                .score(120)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(9)
                                .type(Frame.Type.STRIKE)
                                .score(148)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(10)
                                .type(Frame.Type.STRIKE)
                                .score(167)
                                .rolls(List.of(Roll.strike(), Roll.hit(8), Roll.hit(1)))
                                .build()
                ))
                .build();
        var performanceJohn = PlayerPerformance.builder()
                .playerName(player2)
                .totalScore(151)
                .frames(List.of(
                        Frame.builder()
                                .number(1)
                                .type(Frame.Type.SPARE)
                                .score(16)
                                .rolls(List.of(Roll.hit(3), Roll.hit(7)))
                                .build(),
                        Frame.builder()
                                .number(2)
                                .type(Frame.Type.OPEN)
                                .score(25)
                                .rolls(List.of(Roll.hit(6), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(3)
                                .type(Frame.Type.STRIKE)
                                .score(44)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(4)
                                .type(Frame.Type.OPEN)
                                .score(53)
                                .rolls(List.of(Roll.hit(8), Roll.hit(1)))
                                .build(),
                        Frame.builder()
                                .number(5)
                                .type(Frame.Type.STRIKE)
                                .score(82)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(6)
                                .type(Frame.Type.STRIKE)
                                .score(101)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(7)
                                .type(Frame.Type.OPEN)
                                .score(110)
                                .rolls(List.of(Roll.hit(9), Roll.miss()))
                                .build(),
                        Frame.builder()
                                .number(8)
                                .type(Frame.Type.SPARE)
                                .score(124)
                                .rolls(List.of(Roll.hit(7), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(9)
                                .type(Frame.Type.OPEN)
                                .score(132)
                                .rolls(List.of(Roll.hit(4), Roll.hit(4)))
                                .build(),
                        Frame.builder()
                                .number(10)
                                .type(Frame.Type.STRIKE)
                                .score(151)
                                .rolls(List.of(Roll.strike(), Roll.hit(9), Roll.miss()))
                                .build()
                ))
                .build();
        return Match.builder()
                .players(List.of(performanceJeff, performanceJohn))
                .build();
    }

    public Match buildMatchSample1NotScored(String player1, String player2) {
        var performanceJeff = PlayerPerformance.builder()
                .playerName(player1)
                .frames(List.of(
                        Frame.builder()
                                .number(1)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(2)
                                .type(Frame.Type.SPARE)
                                .rolls(List.of(Roll.hit(7), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(3)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.hit(9), Roll.miss()))
                                .build(),
                        Frame.builder()
                                .number(4)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(5)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.miss(), Roll.hit(8)))
                                .build(),
                        Frame.builder()
                                .number(6)
                                .type(Frame.Type.SPARE)
                                .rolls(List.of(Roll.hit(8), Roll.hit(2)))
                                .build(),
                        Frame.builder()
                                .number(7)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.foul(), Roll.hit(6)))
                                .build(),
                        Frame.builder()
                                .number(8)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(9)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(10)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike(), Roll.hit(8), Roll.hit(1)))
                                .build()
                ))
                .build();
        var performanceJohn = PlayerPerformance.builder()
                .playerName(player2)
                .frames(List.of(
                        Frame.builder()
                                .number(1)
                                .type(Frame.Type.SPARE)
                                .rolls(List.of(Roll.hit(3), Roll.hit(7)))
                                .build(),
                        Frame.builder()
                                .number(2)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.hit(6), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(3)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(4)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.hit(8), Roll.hit(1)))
                                .build(),
                        Frame.builder()
                                .number(5)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(6)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike()))
                                .build(),
                        Frame.builder()
                                .number(7)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.hit(9), Roll.miss()))
                                .build(),
                        Frame.builder()
                                .number(8)
                                .type(Frame.Type.SPARE)
                                .rolls(List.of(Roll.hit(7), Roll.hit(3)))
                                .build(),
                        Frame.builder()
                                .number(9)
                                .type(Frame.Type.OPEN)
                                .rolls(List.of(Roll.hit(4), Roll.hit(4)))
                                .build(),
                        Frame.builder()
                                .number(10)
                                .type(Frame.Type.STRIKE)
                                .rolls(List.of(Roll.strike(), Roll.hit(9), Roll.miss()))
                                .build()
                ))
                .build();
        return Match.builder()
                .players(List.of(performanceJeff, performanceJohn))
                .build();
    }

    public List<Roll> buildHits(int numRolls, int knockedPins) {
        return buildRolls(numRolls, knockedPins, Roll.Type.HIT);
    }

    public List<Roll> buildMisses(int numRolls) {
        return buildRolls(numRolls, 0, Roll.Type.MISS);
    }

    public List<Roll> buildFouls(int numRolls) {
        return buildRolls(numRolls, 0, Roll.Type.FOUL);
    }

    private List<Roll> buildRolls(int numRolls, int knockedPins, Roll.Type rollType) {
        return IntStream.range(0, numRolls)
                .mapToObj(r -> Roll.builder()
                        .type(rollType)
                        .knockedPins(knockedPins)
                        .build())
                .collect(Collectors.toList());
    }
}
