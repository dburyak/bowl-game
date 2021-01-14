package com.dburyak.exercise.game.bowling;

import com.dburyak.exercise.game.bowling.config.CliArgsConfigRetriever;
import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.config.ConfigRetriever;
import com.dburyak.exercise.game.bowling.config.DefaultConfigRetriever;
import com.dburyak.exercise.game.bowling.service.io.GameHistoryInput;
import com.dburyak.exercise.game.bowling.service.format.GameParser;
import com.dburyak.exercise.game.bowling.service.format.GameOutputFormatter;
import com.dburyak.exercise.game.bowling.service.io.GameOutput;
import com.dburyak.exercise.game.bowling.service.rules.ScoreCalculationStrategy;
import com.dburyak.exercise.game.bowling.util.ConfigUtil;
import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(
        name = "bowl-game",
        description = "Calculates bowling game scores",
        mixinStandardHelpOptions = true,
        versionProvider = AppVersionProvider.class
)
@Getter
public class BowlingCliApp implements Callable<Integer> {

    @Parameters(index = "0", arity = "0..1",
            description = "Input source (default: \"stdin\", supported: \"stdin\", filename, URL)")
    private String input;

    @Option(names = {"-if", "--in-format"},
            description = "Input format (default: \"text-tab-separated\", supported: \"text-tab-separated\")")
    private InputFormat inputFormat;

    @Option(names = {"-o", "--out"},
            description = "Output destination (default: \"stdout\", supported: \"stdout\", filename")
    private String output;

    @Option(names = {"-of", "--out-format"},
            description = "Output format (default: \"text-tab-separated\", supported: \"text-tab-separated\"")
    private OutputFormat outputFormat;

    @Option(names = {"-r", "--rules"}, description = "Bowling rules (default: \"ten-pin\", supported: \"ten-pin\")")
    private Rules rules;

    public enum InputFormat {textTabSeparated}

    public enum OutputFormat {textTabSeparated}

    public enum Rules {tenPin}

    /**
     * Main application logic.
     */
    @Override
    public Integer call() throws Exception {
        // configuration
        var configUtil = new ConfigUtil();
        var configRetrievers = List.of(
                new DefaultConfigRetriever(),
                new CliArgsConfigRetriever(this, configUtil)
        );
        var config = Config.merge(configRetrievers.stream()
                .map(ConfigRetriever::retrieve)
                .collect(Collectors.toList()));

        // initialization
        var input = GameHistoryInput.create(config);
        var parser = GameParser.create(config);
        var scoreCalculator = ScoreCalculationStrategy.create(config);
        var outputFormatter = GameOutputFormatter.create(config);
        var output = GameOutput.create(config);

        // main logic
        var game = parser.parse(input);
        scoreCalculator.calculateScores(game);
        outputFormatter.format(game, output);

        return 0;
    }

    public static void main(String[] args) {
        var exitCode = new CommandLine(new BowlingCliApp()).execute(args);
        System.exit(exitCode);
    }
}
