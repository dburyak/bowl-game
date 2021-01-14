package com.dburyak.exercise.game.bowling.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

@AllArgsConstructor
public class FileScoreOutput implements ScoreOutput {
    private File outputFile;

    @Override
    public OutputStream asOutputStream() {
        try {
            var parentDir = outputFile.getParentFile();
            if (parentDir != null) {
                parentDir.mkdirs();
            }
            outputFile.createNewFile();
            return new FileOutputStream(outputFile);
        } catch (IOException e) {
            throw new UncheckedIOException("can not write to output file: " + outputFile, e);
        }
    }
}
