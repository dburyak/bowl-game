package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.MatchTestHelper;
import com.dburyak.exercise.game.bowling.TenPinTabSeparatedInputTestHelper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TenPinTabSeparatedParserTest {
    private TenPinTabSeparatedParser parser = new TenPinTabSeparatedParser();

    private MatchHistoryInput matchInput = mock(MatchHistoryInput.class);

    private TenPinTabSeparatedInputTestHelper inputHelper = new TenPinTabSeparatedInputTestHelper();
    private MatchTestHelper matchTestHelper = new MatchTestHelper();

    @Test
    void parse_ParsesCorrectly_WhenAllStrikesMatch() {
        // given: text input for all strikes match
        var inputStr = inputHelper.buildInputAllStrikes("p1", "p2");
        var reader = new StringReader(inputStr);
        when(matchInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var match = parser.parse(matchInput);

        // then: parsed correctly
        assertThat(match)
                .isEqualTo(matchTestHelper.buildMatchAllStrikesNotScored("p1", "p2"));
    }

    @Test
    void parse_ParsesCorrectly_WhenAllFoulsMatch() {
        // given: text input for all fouls match
        var inputStr = inputHelper.buildInputAllFouls("p1", "p2");
        var reader = new StringReader(inputStr);
        when(matchInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var match = parser.parse(matchInput);

        // then: parsed correctly
        assertThat(match)
                .isEqualTo(matchTestHelper.buildMatchAllFouls("p1", "p2"));
    }

    @Test
    void parse_ParsesCorrectly_WhenSample1Match() {
        // given: input for sample1 match
        var player1 = "Jeff";
        var player2 = "John";
        var inputStr = inputHelper.buildInputSample1Match(player1, player2);
        var reader = new StringReader(inputStr);
        when(matchInput.asInputReader()).thenReturn(reader);

        // when: parse it
        var match = parser.parse(matchInput);

        // then: parsed match has valid data
        assertThat(match)
                .isEqualTo(matchTestHelper.buildMatchSample1NotScored(player1, player2));
    }

    @Test
    void parse_Fails_WhenNotEnoughRolls() {
        // given: well formed text input for match, but with not enough rolls for 10-pin rules
        var inputStr = inputHelper.buildInputTooFewRolls("p1");
        var reader = new StringReader(inputStr);
        when(matchInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(matchInput))

        // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("too few rolls");
    }

    @Test
    void parse_Fails_WhenTooManyRolls() {
        // given: well formed text input for match, but with too many rolls for 10-pin rules
        var inputStr = inputHelper.buildInputTooManyRolls("p1");
        var reader = new StringReader(inputStr);
        when(matchInput.asInputReader()).thenReturn(reader);

        // when: parse it
        Assertions.assertThatThrownBy(() -> parser.parse(matchInput))

                // then: format exception is thrown
                .isInstanceOf(FormatException.class)
                .hasMessageContaining("too many rolls");
    }
}
