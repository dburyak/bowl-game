package com.dburyak.exercise.game.bowling.service.io;

import lombok.AllArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

@AllArgsConstructor
public class FileGameOutput implements GameOutput {
    private File outputFile;

    @Override
    public OutputStream outputStream() {
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
