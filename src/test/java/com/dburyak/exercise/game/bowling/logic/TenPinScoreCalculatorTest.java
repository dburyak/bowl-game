package com.dburyak.exercise.game.bowling.logic;

import com.dburyak.exercise.game.bowling.MatchTestHelper;
import com.dburyak.exercise.game.bowling.domain.Frame;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TenPinScoreCalculatorTest {
    private TenPinScoreCalculator scoreCalculator = new TenPinScoreCalculator();

    private MatchTestHelper matchHelper = new MatchTestHelper();

    @Test
    void calculateScore_CalculatesCorrectly_WhenAllFoulsMatch() {
        // given: match with all fouls
        var match = matchHelper.buildMatchAllFouls("p1", "p2");

        // when: calculate scores
        scoreCalculator.calculateScores(match);

        // then: score contains all zeroes
        var player1Scores = match.getPlayers().get(0).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var player2Scores = match.getPlayers().get(1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());

        assertThat(player1Scores)
                .containsOnly(0);
        assertThat(player2Scores)
                .containsOnly(0);
    }

    @Test
    void calculateScore_CalculatesCorrectly_WhenAllStrikesMatch() {
        // given: match with all strikes
        var match = matchHelper.buildMatchAllStrikesNotScored("p1", "p2");

        // when: calculate scores for the match
        scoreCalculator.calculateScores(match);

        // then: score contains correct values
        var player1Scores = match.getPlayers().get(0).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var player2Scores = match.getPlayers().get(1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var expectedScores = List.of(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);

        assertThat(player1Scores)
                .containsExactlyElementsOf(expectedScores);
        assertThat(player2Scores)
                .containsExactlyElementsOf(expectedScores);
    }

    @Test
    void calculate_CalculatesCorrectly_WhenSample1Match() {
        // given: sample1 match
        var player1 = "Jeff";
        var player2 = "John";
        var match = matchHelper.buildMatchSample1NotScored(player1, player2);

        // extra check: make sure that no any scores are pre-calculated
        var jeffScoresBeforeCalculation = match.getPlayerPerformance(player1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var johnScoresBeforeCalculation = match.getPlayerPerformance(player2).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        assertThat(jeffScoresBeforeCalculation)
                .containsOnly(0);
        assertThat(johnScoresBeforeCalculation)
                .containsOnly(0);


        // when: calculate scores of the match
        scoreCalculator.calculateScores(match);


        // then: scores are correct
        var jeffScores = match.getPlayerPerformance(player1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var johnScores = match.getPlayerPerformance(player2).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var jeffExpectedScores = List.of(20, 39, 48, 66, 74, 84, 90, 120, 148, 167);
        var johnExpectedScores = List.of(16, 25, 44, 53, 82, 101, 110, 124, 132, 151);

        assertThat(jeffScores)
                .containsExactlyElementsOf(jeffExpectedScores);
        assertThat(johnScores)
                .containsExactlyElementsOf(johnExpectedScores);
    }
}
