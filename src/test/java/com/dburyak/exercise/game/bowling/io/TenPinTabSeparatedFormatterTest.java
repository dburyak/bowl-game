package com.dburyak.exercise.game.bowling.io;

import com.dburyak.exercise.game.bowling.MatchTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TenPinTabSeparatedFormatterTest {
    private TenPinTabSeparatedFormatter formatter = new TenPinTabSeparatedFormatter();

    private MatchTestHelper matchHelper = new MatchTestHelper();
    private ScoreOutput scoreOut = mock(ScoreOutput.class);
    private Writer scoreWriter = new StringWriter();

    @BeforeEach
    void setup() {
        when(scoreOut.asOutputWriter()).thenReturn(scoreWriter);
    }

    @Test
    void format_FormatsCorrectly_WhenAllStrikes() {
        // given: match with all strikes
        var match = matchHelper.buildMatchAllStrikes("p1", "p2");

        // when: when format such match
        formatter.format(match, scoreOut);

        // then: formatted correctly
        var outStr = scoreWriter.toString();
        var expectedStr = "" +
                "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "p1\n" +
                "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n" +
                "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n" +
                "p2\n" +
                "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n" +
                "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n";
        assertThat(outStr)
                .isEqualTo(expectedStr);
    }

    @Test
    void format_FormatsCorrectly_WhenAllFouls() {
        // given: match with all fouls
        var match = matchHelper.buildMatchAllFouls("p1", "p2");

        // when: format match
        formatter.format(match, scoreOut);

        // then: formatted correctly
        var outStr = scoreWriter.toString();
        var expectedStr = "" +
                "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "p1\n" +
                "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\n" +
                "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n" +
                "p2\n" +
                "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\n" +
                "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n";
        assertThat(outStr)
                .isEqualTo(expectedStr);
    }

    @Test
    void format_FormatsCorrectly_WhenSampleMatch1() {
        // given: sample1 match
        var match = matchHelper.buildMatchSample1();

        // when: format match
        formatter.format(match, scoreOut);

        // then: formatted correctly
        var outStr = scoreWriter.toString();
        var expectedStr = "" +
                "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
                "Jeff\n" +
                "Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\n" +
                "Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\n" +
                "John\n" +
                "Pinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0\n" +
                "Score\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\n";
        assertThat(outStr)
                .isEqualTo(expectedStr);
    }
}
