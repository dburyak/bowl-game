package com.dburyak.exercise.game.bowling;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import picocli.CommandLine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.Random;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingCliAppIntegTest {
    private ByteArrayOutputStream capturedStdoutBytes = new ByteArrayOutputStream();
    private String jeffVsJohnInputFilePath = getClass().getResource("/game-jeff-vs-john.txt").getFile();
    private File outFile = new File("build/integration-test/test-" + new Random().nextInt() + ".txt");

    @BeforeEach
    void setup() throws IOException {
        // redirect stdout to captured buffer
        System.setOut(new PrintStream(capturedStdoutBytes));

        // create out file
        var outFileDir = outFile.getParentFile();
        if (outFileDir != null) {
            outFileDir.mkdirs();
        }
        outFile.createNewFile();
    }

    @AfterEach
    void cleanup() {
        // restore stdout and stdin back to system defaults
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.setIn(new FileInputStream(FileDescriptor.in));

        // clean up out file
        outFile.delete();
    }

    /**
     * stdin -> stdout
     */
    @ParameterizedTest
    @ArgumentsSource(TextInputAndTextOutArgs.class)
    void runApp_PrintsScoresCorrectly_WhenStdinInput_AndStdoutOutput(String stdinInput, String expectedStdoutOutput) {
        // given: cli args for app
        // -- read input from stdin
        // -- use 10-pin rules
        // -- print output to stdout
        String[] args = {}; // no args - means use by default stdin and stdout, 10pin
        System.setIn(new ByteArrayInputStream(stdinInput.getBytes()));

        // when: run app with no cli args
        new CommandLine(BowlingCliApp.class).execute(args);

        // then: correct scores output received in stdout
        var outStr = capturedStdoutBytes.toString();
        assertThat(outStr)
                .isEqualTo(expectedStdoutOutput);
    }

    /**
     * stdin -> file
     */
    @ParameterizedTest
    @ArgumentsSource(TextInputAndTextOutArgs.class)
    void runApp_PrintsScoresCorrectly_WhenStdinInput_AndFileOutput(String stdinInput, String expectedFileOutput)
            throws IOException {
        // given: cli args for app
        // -- read input from stdin
        // -- use 10-pin rules
        // -- print output to file
        String[] args = {"-o", outFile.getAbsolutePath()};
        System.setIn(new ByteArrayInputStream(stdinInput.getBytes()));

        // when: run app with no cli args
        new CommandLine(BowlingCliApp.class).execute(args);

        // then: correct scores output is written to out file
        var outStr = new String(Files.readAllBytes(outFile.toPath()));
        assertThat(outStr)
                .isEqualTo(expectedFileOutput);
    }

    /**
     * file -> stdout
     */
    @ParameterizedTest
    @ArgumentsSource(FileInputAndTextOutArgs.class)
    void runApp_PrintsScoresCorrectly_WhenJeffVsJohnScenarioGame_FileInputAndStdoutOutput(String inputFileName,
            String expectedStdoutOutput) {
        // given: cli args for app
        // -- read input from input file
        // -- use 10-pin rules
        // -- print output to stdout
        String[] args = {getClass().getResource(inputFileName).getFile()};

        // when: run app with cli args
        new CommandLine(BowlingCliApp.class).execute(args);

        // then: correct output received in stdout
        var outStr = capturedStdoutBytes.toString();
        assertThat(outStr)
                .isEqualTo(expectedStdoutOutput);
    }

    /**
     * file -> file
     */
    @ParameterizedTest
    @ArgumentsSource(FileInputAndTextOutArgs.class)
    void runApp_PrintsScoresCorrectly_WhenJeffVsJohnScenarioGame_FileInputAndFileOutput(String inputFileName,
            String expectedFileOutput) throws IOException {
        // given: cli args for app
        // -- read input from input file
        // -- use 10-pin rules
        // -- print output to file
        String[] args = {getClass().getResource(inputFileName).getFile(), "-o", outFile.getAbsolutePath()};

        // when: run app with cli args
        new CommandLine(BowlingCliApp.class).execute(args);

        // then: correct output received in out file
        var outStr = new String(Files.readAllBytes(outFile.toPath()));
        assertThat(outStr)
                .isEqualTo(expectedFileOutput);
    }


    private static class TextInputAndTextOutArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(JEFF_VS_JOHN_INPUT_STR, EXPECTED_JEFF_VS_JOHN_SCENARIO_SCORE_OUT_STR),
                    Arguments.of(ALL_STRIKES_MATCH_INPUT_STR, EXPECTED_ALL_STRIKES_MATCH_OUT_STR),
                    Arguments.of(ALL_FOULS_MATCH_INPUT_STR, EXPECTED_ALL_FOULS_MATCH_OUT_STR),
                    Arguments.of(ALL_ZEROES_MATCH_INPUT_STR, EXPECTED_ALL_ZEROES_MATCH_OUT_STR)
            );
        }
    }

    private static class FileInputAndTextOutArgs implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of("/game-jeff-vs-john.txt", EXPECTED_JEFF_VS_JOHN_SCENARIO_SCORE_OUT_STR),
                    Arguments.of("/game-all-strikes.txt", EXPECTED_ALL_STRIKES_MATCH_OUT_STR),
                    Arguments.of("/game-all-fouls.txt", EXPECTED_ALL_FOULS_MATCH_OUT_STR),
                    Arguments.of("/game-all-zeroes.txt", EXPECTED_ALL_ZEROES_MATCH_OUT_STR)
            );
        }
    }

    private static final String EXPECTED_JEFF_VS_JOHN_SCENARIO_SCORE_OUT_STR = "" +
            "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
            "Jeff\n" +
            "Pinfalls\t\tX\t7\t/\t9\t0\t\tX\t0\t8\t8\t/\tF\t6\t\tX\t\tX\tX\t8\t1\n" +
            "Score\t\t20\t\t39\t\t48\t\t66\t\t74\t\t84\t\t90\t\t120\t\t148\t\t167\n" +
            "John\n" +
            "Pinfalls\t3\t/\t6\t3\t\tX\t8\t1\t\tX\t\tX\t9\t0\t7\t/\t4\t4\tX\t9\t0\n" +
            "Score\t\t16\t\t25\t\t44\t\t53\t\t82\t\t101\t\t110\t\t124\t\t132\t\t151\n";
    private static final String JEFF_VS_JOHN_INPUT_STR = "" +
            "Jeff\t10\n" +
            "John\t3\n" +
            "John\t7\n" +
            "Jeff\t7\n" +
            "Jeff\t3\n" +
            "John\t6\n" +
            "John\t3\n" +
            "Jeff\t9\n" +
            "Jeff\t0\n" +
            "John\t10\n" +
            "Jeff\t10\n" +
            "John\t8\n" +
            "John\t1\n" +
            "Jeff\t0\n" +
            "Jeff\t8\n" +
            "John\t10\n" +
            "Jeff\t8\n" +
            "Jeff\t2\n" +
            "John\t10\n" +
            "Jeff\tF\n" +
            "Jeff\t6\n" +
            "John\t9\n" +
            "John\t0\n" +
            "Jeff\t10\n" +
            "John\t7\n" +
            "John\t3\n" +
            "Jeff\t10\n" +
            "John\t4\n" +
            "John\t4\n" +
            "Jeff\t10\n" +
            "Jeff\t8\n" +
            "Jeff\t1\n" +
            "John\t10\n" +
            "John\t9\n" +
            "John\t0\n";
    private static final String ALL_STRIKES_MATCH_INPUT_STR = "" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n" +
            "LuckyOne\t10\n" +
            "LuckyToo\t10\n";
    private static final String EXPECTED_ALL_STRIKES_MATCH_OUT_STR = "" +
            "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
            "LuckyOne\n" +
            "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n" +
            "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n" +
            "LuckyToo\n" +
            "Pinfalls\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\t\tX\tX\tX\tX\n" +
            "Score\t\t30\t\t60\t\t90\t\t120\t\t150\t\t180\t\t210\t\t240\t\t270\t\t300\n";
    private static final String ALL_FOULS_MATCH_INPUT_STR = "" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n" +
            "UnluckyOne\tF\n" +
            "UnluckyToo\tF\n";
    private static final String EXPECTED_ALL_FOULS_MATCH_OUT_STR = "" +
            "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
            "UnluckyOne\n" +
            "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\n" +
            "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n" +
            "UnluckyToo\n" +
            "Pinfalls\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\tF\n" +
            "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n";
    private static final String ALL_ZEROES_MATCH_INPUT_STR = "" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n" +
            "UnluckyOne\t0\n" +
            "UnluckyToo\t0\n";
    private static final String EXPECTED_ALL_ZEROES_MATCH_OUT_STR = "" +
            "Frame\t\t1\t\t2\t\t3\t\t4\t\t5\t\t6\t\t7\t\t8\t\t9\t\t10\n" +
            "UnluckyOne\n" +
            "Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\n" +
            "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n" +
            "UnluckyToo\n" +
            "Pinfalls\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\t0\n" +
            "Score\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\t\t0\n";
}
