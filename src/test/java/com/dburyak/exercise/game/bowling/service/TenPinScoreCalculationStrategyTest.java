package com.dburyak.exercise.game.bowling.service;

import com.dburyak.exercise.game.bowling.GameTestHelper;
import com.dburyak.exercise.game.bowling.domain.Frame;
import com.dburyak.exercise.game.bowling.service.rules.TenPinScoreCalculationStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class TenPinScoreCalculationStrategyTest {
    private TenPinScoreCalculationStrategy scoreCalculator = new TenPinScoreCalculationStrategy();

    private GameTestHelper gameHelper = new GameTestHelper();

    @Test
    void calculateScore_CalculatesCorrectly_WhenAllFoulsGame() {
        // given: game with all foulsTenPinTabSeparatedParserTest
        var game = gameHelper.buildGameAllFouls("p1", "p2");

        // when: calculate scores
        scoreCalculator.calculateScores(game);

        // then: score contains all zeroes
        var player1Scores = game.getPlayers().get(0).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var player2Scores = game.getPlayers().get(1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());

        assertThat(player1Scores)
                .containsOnly(0);
        assertThat(player2Scores)
                .containsOnly(0);
    }

    @Test
    void calculateScore_CalculatesCorrectly_WhenAllStrikesGame() {
        // given: game with all strikes
        var game = gameHelper.buildGameAllStrikesNotScoresCalculated("p1", "p2");

        // when: calculate scores for the game
        scoreCalculator.calculateScores(game);

        // then: score contains correct values
        var player1Scores = game.getPlayers().get(0).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var player2Scores = game.getPlayers().get(1).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var expectedAllStrikesScores = List.of(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);

        assertThat(player1Scores)
                .containsExactlyElementsOf(expectedAllStrikesScores);
        assertThat(player2Scores)
                .containsExactlyElementsOf(expectedAllStrikesScores);
    }

    @Test
    void calculate_CalculatesCorrectly_WhenJeffVsJohnGame() {
        // given: JeffVsJohn scenario game
        var jeff = "Jeff";
        var john = "John";
        var game = gameHelper.buildNotScoredGameJeffVsJohnScenario(jeff, john);

        // extra check: make sure that no any scores are pre-calculated
        var jeffScoresBeforeCalculation = game.getPlayerPerformance(jeff).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var johnScoresBeforeCalculation = game.getPlayerPerformance(john).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        assertThat(jeffScoresBeforeCalculation)
                .containsOnly(0);
        assertThat(johnScoresBeforeCalculation)
                .containsOnly(0);


        // when: calculate scores of the game
        scoreCalculator.calculateScores(game);


        // then: scores are correct
        var jeffScores = game.getPlayerPerformance(jeff).getFrames().stream()
                .map(Frame::getScore)
                .collect(Collectors.toList());
        var johnScores = game.getPlayerPerformance(john).getFrames().stream()
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
