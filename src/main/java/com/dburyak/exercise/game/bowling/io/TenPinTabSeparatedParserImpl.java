package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.domain.Frame;
import com.dburyak.exercise.game.bowling.domain.Match;
import com.dburyak.exercise.game.bowling.domain.PlayerPerformance;
import com.dburyak.exercise.game.bowling.domain.Roll;
import lombok.Builder;
import lombok.Data;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default input format - tab separated text, ten pin rules.
 */
public class TenPinTabSeparatedParserImpl implements MatchHistoryParser {
    private static final String SEPARATOR = "\t";

    @Override
    public Match parse(MatchHistoryInput input) {
        var inputReader = new BufferedReader(input.asInputReader());
        var performances = inputReader.lines()
                .map(line -> {
                    var items = line.split(SEPARATOR);
                    if (items.length != 2) {
                        throw new FormatException("malformed input entry: " + line);
                    }
                    return new Entry(items[0], items[1]);
                })
                .collect(Collectors.groupingBy(Entry::getPlayerName))
                .entrySet().stream()
                .map(playerRollEntries -> {
                    var playerName = playerRollEntries.getKey();
                    var rolls = playerRollEntries.getValue().stream()
                            .map(this::toRoll)
                            .collect(Collectors.toList());
                    var frames = toFrames(rolls);
                    return PlayerPerformance.builder()
                            .playerName(playerName)
                            .frames(frames)
                            .build();
                })
                .collect(Collectors.toList());
        return Match.builder()
                .players(performances)
                .build();
    }

    @Data
    @Builder(toBuilder = true)
    private static class Entry {
        private String playerName;
        private String rollResult;
    }

    public Roll toRoll(Entry e) {
        var knockedPins = 0;
        var type = Roll.Type.HIT;
        if (e.rollResult.equalsIgnoreCase("f")) {
            type = Roll.Type.FOUL;
        } else {
            try {
                knockedPins = Integer.parseInt(e.rollResult);
                var isValidNumPins = (0 <= knockedPins) && (knockedPins <= 10);
                if (!isValidNumPins) {
                    throw new FormatException("number of rolls is out of range: " + e);
                }
                type = knockedPins > 0 ? Roll.Type.HIT : Roll.Type.MISS;
            } catch (NumberFormatException err) {
                throw new FormatException("malformed number of rolls in input entry: " + e, err);
            }
        }
        return Roll.builder()
                .type(type)
                .knockedPins(knockedPins)
                .build();
    }

    public List<Frame> toFrames(List<Roll> allPlayerRolls) {
        var frames = new ArrayList<Frame>();
        var rollPos = 0;
        var frameNumber = 1;
        for (int i = 0; i < 10; i++) {
            var frame = Frame.builder()
                    .number(frameNumber)
                    .build();
            var rollsInFrame = addRollsToFrame(frame, allPlayerRolls, rollPos);
            rollPos += rollsInFrame;
            frames.add(frame);
            frameNumber++;
        }
        return frames;
    }

    private int addRollsToFrame(Frame frame, List<Roll> rolls, int offset) {
        frame.setRolls(new ArrayList<>());
        var firstRoll = rolls.get(offset);
        frame.addRoll(firstRoll);
        if (frame.getNumber() < 10) { // non-final frame, middle of the game
            // mid-game frame may have either 1 or 2 rolls
            if (firstRoll.getKnockedPins() == 10) { // strike, single roll in frame
                frame.setType(Frame.Type.STRIKE);
            } else { // spare or open frame, 2 rolls in frame
                var secondRoll = rolls.get(offset + 1);
                frame.addRoll(secondRoll);
                var pinsKnockedInTwoRolls = firstRoll.getKnockedPins() + secondRoll.getKnockedPins();
                frame.setType(pinsKnockedInTwoRolls == 10 ? Frame.Type.SPARE : Frame.Type.OPEN);
            }
        } else { // this is final frame, it requires special handling
            // final frame always has at least 2 rolls
            var secondRoll = rolls.get(offset + 1);
            frame.addRoll(secondRoll);
            var pinsKnockedInTwoRolls = firstRoll.getKnockedPins() + secondRoll.getKnockedPins();
            var isStrike = firstRoll.getKnockedPins() == 10;
            if (isStrike || pinsKnockedInTwoRolls == 10) {
                // last frame strike or spare - always 3 rolls in frame
                var thirdRoll = rolls.get(offset + 2);
                frame.addRoll(thirdRoll);
                frame.setType(isStrike ? Frame.Type.STRIKE : Frame.Type.SPARE);
            } else { // final open frame - 2 rolls
                frame.setType(Frame.Type.OPEN);
            }
        }
        return frame.getRollsNumber();
    }
}
