package com.dburyak.exercise.game.bowling;

import com.dburyak.exercise.game.bowling.config.CliArgsConfigRetriever;
import com.dburyak.exercise.game.bowling.config.Config;
import com.dburyak.exercise.game.bowling.config.ConfigRetriever;
import com.dburyak.exercise.game.bowling.config.DefaultConfigRetriever;
import com.dburyak.exercise.game.bowling.util.ConfigUtil;
import lombok.Getter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(
        name = "bowl-game",
        description = "Calculates bowling game scores",
        mixinStandardHelpOptions = true,
        versionProvider = BowlingCliApp.VersionProvider.class
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

    @Override
    public Integer call() throws Exception {
        var configUtil = new ConfigUtil();
        var configRetrievers = List.of(
                new DefaultConfigRetriever(),
                new CliArgsConfigRetriever(this, configUtil)
        );
        var config = Config.merge(configRetrievers.stream()
                .map(ConfigRetriever::retrieve)
                .collect(Collectors.toList()));
        // TODO: implement logic here
        return 0;
    }

    public static void main(String[] args) {
        var exitCode = new CommandLine(new BowlingCliApp()).execute(args);
        System.exit(exitCode);
    }

    static class VersionProvider implements IVersionProvider {
        private static final String versionFileName = "/version.txt";
        private static final String revisionFileName = "/revision.txt";
        private static final String buildTimeFileName = "/built_at.txt";

        @Override
        public String[] getVersion() throws Exception {
            // using linked hash map to preserve the order
            var versionInfoEntries = new LinkedHashMap<String, String>();
            versionInfoEntries.put("version  : ", versionFileName);
            versionInfoEntries.put("revision : ", revisionFileName);
            versionInfoEntries.put("built_at : ", buildTimeFileName);
            return versionInfoEntries.entrySet().stream()
                    .map(e -> {
                        String versionLabel = e.getKey();
                        String fileName = e.getValue();
                        String versionValue = readSingleLine(fileName);
                        return versionLabel + versionValue;
                    })
                    .toArray(String[]::new);
        }

        private String readSingleLine(String fileName) {
            var line = "unknown";
            try (
                    var inStream = getClass().getResourceAsStream(fileName);
                    var reader = new BufferedReader(new InputStreamReader(inStream));
            ) {
                line = reader.readLine();
            } catch (IOException ioException) {
                // we really don't care here, just default to "unknown"
            }
            return line;
        }
    }
}
