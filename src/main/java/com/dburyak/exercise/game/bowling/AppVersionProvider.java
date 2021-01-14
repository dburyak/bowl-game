package com.dburyak.exercise.game.bowling;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

/**
 * Version information reader.
 */
class AppVersionProvider implements CommandLine.IVersionProvider {
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
