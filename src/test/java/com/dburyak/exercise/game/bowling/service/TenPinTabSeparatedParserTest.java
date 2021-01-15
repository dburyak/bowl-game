package com.dburyak.exercise.game.bowling.service;

import com.dburyak.exercise.game.bowling.GameTestHelper;
import com.dburyak.exercise.game.bowling.TenPinTabSeparatedInputTestHelper;
import com.dburyak.exercise.game.bowling.service.format.TenPinTabSeparatedParser;
import com.dburyak.exercise.game.bowling.service.io.GameHistoryInput;
import com.dburyak.exercise.game.bowling.util.FormatException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.StringReader;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TenPinTabSeparatedParserTest {
    private TenPinTabSeparatedParser parser = new TenPinTabSeparatedParser();

    private GameHistoryInput gameInput = mock(GameHistoryInput.class);

    private TenPinTabSeparatedInputTestHelper inputHelper = new TenPinTabSeparatedInputTestHelper();
    private GameTestHelper gameTestHelper = new GameTestHelper();

    @Test
    void parse_ParsesCorrectly_WhenAllStrikesGame() {
        // given: text input for all strikes game
        var inputStr = inputHelper.buildInputAllStrikes("p1", "p2");
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var game = parser.parse(gameInput);

        // then: parsed correctly
        var expectedGameWithAllStrikes = gameTestHelper.buildGameAllStrikesNotScoresCalculated("p1", "p2");

        assertThat(game)
                .isEqualTo(expectedGameWithAllStrikes);
    }

    @Test
    void parse_ParsesCorrectly_WhenAllFoulsGame() {
        // given: text input for all fouls game
        var inputStr = inputHelper.buildInputAllFouls("p1", "p2");
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var game = parser.parse(gameInput);

        // then: parsed correctly
        var expectedGameWithAllFouls = gameTestHelper.buildGameAllFouls("p1", "p2");
        assertThat(game)
                .isEqualTo(expectedGameWithAllFouls);
    }

    @Test
    void parse_ParsesCorrectly_WhenJeffVsJohnGame() {
        // given: input for JeffVsJohn scenario game
        var jeff = "Jeff";
        var john = "John";
        var inputStr = inputHelper.buildInputForJeffVsJohnScenarioGame(jeff, john);
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var game = parser.parse(gameInput);

        // then: parsed game has valid data
        var expectedJeffVsJohnGame = gameTestHelper.buildNotScoredGameJeffVsJohnScenario(jeff, john);
        assertThat(game)
                .isEqualTo(expectedJeffVsJohnGame);
    }

    @Test
    void parse_Fails_WhenNotEnoughRolls() {
        // given: well formatted text input for game, but with not enough rolls for 10-pin rules
        var inputStr = inputHelper.buildInputTooFewRolls("p1");
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(gameInput))

                // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("too few rolls");
    }

    @Test
    void parse_Fails_WhenTooManyRolls() {
        // given: well formed text input for game, but with too many rolls for 10-pin rules
        var inputStr = inputHelper.buildInputTooManyRolls("p1");
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(gameInput))

                // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("too many rolls");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, 11, 100, Integer.MIN_VALUE, Integer.MAX_VALUE})
    void parse_Fails_WhenInputContainsOutOfRangePinfalls(int invalidPinfalls) {
        // given: game input with negative knocked pins
        var inputStr = "Carl " + invalidPinfalls;
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(gameInput))

                // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("malformed input entry")
                .hasMessageContaining("Carl")
                .hasMessageContaining(Objects.toString(invalidPinfalls));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A", "zZz", "Carl"})
    void parse_Fails_WhenInputContainsIllegalSymbolsForRollResult(String illegalSymbols) {
        // given: game input with illegal symbol (not "F") on knocked pins place
        var inputStr = "Carl " + illegalSymbols;
        var reader = new StringReader(inputStr);
        when(gameInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(gameInput))

                // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("malformed input entry")
                .hasMessageContaining("Carl")
                .hasMessageContaining(illegalSymbols);
    }
}
