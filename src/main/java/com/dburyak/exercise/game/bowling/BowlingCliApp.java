package com.dburyak.exercise.game.bowling;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.IVersionProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.Callable;

@Command(
        name = "bowl-game",
        description = "Calculates bowling game scores",
        mixinStandardHelpOptions = true,
        versionProvider = BowlingCliApp.VersionProvider.class
)
public class BowlingCliApp implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
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
            return Map
                    .of(
                            "version  : ", versionFileName,
                            "revision : ", revisionFileName,
                            "built_at : ", buildTimeFileName
                    )
                    .entrySet().stream()
                    .map(e -> {
                        String versionType = e.getKey();
                        String fileName = e.getValue();
                        String versionValue;
                        try (
                                var inStream = getClass().getResourceAsStream(fileName);
                                var reader = new BufferedReader(new InputStreamReader(inStream));
                                ) {
                            versionValue = reader.readLine();
                        } catch (IOException ioException) {
                            versionValue = "unknown";
                        }
                        return versionType + " " + versionValue;
                    })
                    .toArray(String[]::new);
        }
    }
}
